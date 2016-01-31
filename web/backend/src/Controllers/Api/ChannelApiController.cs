using AutoMapper;
using src.Controllers.Abstract;
using src.Helpers.Api.Response;
using src.Models.Api;
using src.Models.ChannelModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using src.Helpers.Api.Results;
using src.Helpers.Api.Authentication.Tokens.Attributes;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity;
using System.Data.Entity.Infrastructure;
using System.Diagnostics;
using System.Data.Entity;
using src.Helpers.Api.Models.Enums;
using MongoDB.Bson;
using MongoDB.Driver;
using Newtonsoft.Json;
using MongoDB.Bson.Serialization;
using System.Dynamic;
using System.Data.Objects;
using src.Helpers.Api.Expando;
using src.Helpers.Api.Hubs;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/channels")]
    public class ChannelApiController : AbstractMongoController
    {
        [HttpPost, Route("join"), Authorize]
        public async Task<IHttpActionResult> JoinChannel(JoinChannelApiModel model)
        {
            if (model != null && ModelState.IsValid)
            {
                var userId = User.Identity.GetUserId();

                var currentUser = await userManager.FindByIdAsync(userId);

                if (model.Price > 0)
                {
                    var result = currentUser.Money - model.Price;

                    if (result > 0)
                    {
                        currentUser.Money = result;

                        var users = (from user in db.Channels select user.UserId).Distinct();

                        float funds = model.Price / users.Count();

                        foreach (var user in users)
                        {
                            var current = await db.Users.FirstAsync(x => x.Id.Equals(user));

                            current.Money += funds;
                        }
                        try
                        {
                            await db.SaveChangesAsync();
                        }
                        catch (Exception e)
                        {
                            Debug.WriteLine(e.Message);
                        }

                        var channel = currentUser.Channels.First(x => x.ChannelId.Equals(model.ChannelId));

                        channel.Price = model.Price;

                        await db.SaveChangesAsync();

                        return this.Ok(new ApiResponse(200, model));
                    }
                }
                else
                {
                    currentUser.Channels.Add(new Models.UserChannel
                    {
                        Price = model.Price,
                        MaxPayedRows = model.MaxPayedRows,
                        ChannelId = model.ChannelId
                    });

                    await db.SaveChangesAsync();

                    return this.Ok(new ApiResponse(200, model));
                }
            }
            return this.BadRequest(new ApiResponse(400, model));
        }

        [HttpGet, Route("me"), Authorize]
        public IHttpActionResult JoinedChannels()
        {
            var channels = CurrentUser.Channels;

            var mapped = Mapper.Map<IEnumerable<UserChannelApiModel>>(channels);

            return Ok(new ApiResponse(200, mapped.ToArray()));
        }

        [HttpPost, Route("heartrate")]
        public async Task<IHttpActionResult> HeartRate(HeartRateApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("heartrate");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<HeartRateModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                List<object> response = new List<object>();

                var channel = await db.Channels.FirstAsync(x => x.ChannelId.Equals(1) && x.UserId.Equals(userId));

                List<HeartRateModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<HeartRateModelExtended>(new BsonDocument(), new FindOptions<object, HeartRateModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    data = await (await collection.FindAsync<HeartRateModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                NotificationHub.GetHubContext().Clients.All.heartrate(new ApiResponse(200, data.ToArray()));

                return Ok(new ApiResponse(200, mapped));
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpPost, Route("fridge")]
        public async Task<IHttpActionResult> Fridge(FridgeApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("fridge");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<FridgeModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                List<object> response = new List<object>();

                var channel = await db.Channels.FirstAsync(x => x.ChannelId.Equals(2) && x.UserId.Equals(userId));

                List<FridgeModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<FridgeModelExtended>(new BsonDocument(), new FindOptions<object, FridgeModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    try
                    {
                        data = await (await collection.FindAsync<FridgeModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();
                    }
                    catch (Exception e)
                    {
                        Debug.WriteLine(e.Message);
                    }

                if (data != null)
                {
                    var distinct = data.Select(x => x.Created.ToShortDateString()).Distinct();

                    foreach (var date in distinct)
                    {
                        int count = 0;
                        var obj = new ExpandoObject();

                        foreach (var item in data)
                        {
                            if (item.Created.ToShortDateString().Equals(date))
                                count += 1;
                        }

                        obj.AddProp("count", count);
                        obj.AddProp("date", DateTime.Parse(date));

                        response.Add(obj);
                    }

                    NotificationHub.GetHubContext().Clients.All.fridge(new ApiResponse(200, response.ToArray()));

                    return Ok(new ApiResponse(200, mapped));
                }
                else
                {
                    return this.NotFound(new ApiResponse(404));
                }
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpPost, Route("movement")]
        public async Task<IHttpActionResult> Movement(MovementApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("movement");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<MovementModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                List<object> response = new List<object>();

                var channel = await db.Channels.FirstAsync(x => x.ChannelId.Equals(3) && x.UserId.Equals(userId));

                List<MovementModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<MovementModelExtended>(new BsonDocument(), new FindOptions<object, MovementModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    data = await (await collection.FindAsync<MovementModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                for (int i = 1; i <= 24; i++)
                {
                    int count = 0;
                    var obj = new ExpandoObject();

                    foreach (var date in data)
                    {
                        if (i.Equals(date.Created.Hour))
                        {
                            count += 1;
                            if (((IDictionary<string, object>)obj).ContainsKey("date"))
                            {
                                ((IDictionary<string, object>)obj)["date"] = date.Created;
                            }
                            else
                            {
                                obj.AddProp("date", date.Created);
                            }
                        }
                    }
                    var dict = ((IDictionary<string, object>)obj);

                    if (dict.ContainsKey("date"))
                    {
                        obj.AddProp("hour", ((DateTime)dict["date"]).Hour);
                        obj.AddProp("count", count);

                        response.Add(obj);
                    }
                }

                NotificationHub.GetHubContext().Clients.All.movement(new ApiResponse(200, response.ToArray()));

                return Ok(new ApiResponse(200, mapped));
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpPost, Route("location")]
        public async Task<IHttpActionResult> Location(LocationApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("location");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<LocationModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                NotificationHub.GetHubContext().Clients.All.location(mapped);

                return Ok(new ApiResponse(200, mapped));
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpPost, Route("light")]
        public async Task<IHttpActionResult> Light(LightApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("light");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<LightModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                var user = await userManager.FindByIdAsync(userId);

                var channel = user.Channels.FirstOrDefault(x => x.ChannelId.Equals(5) && x.UserId.Equals(userId));

                if (channel != null)
                {
                    List<LightModelExtended> data = null;

                    if (channel.Price > 0)
                    {
                        var result = (await collection.FindAsync<LightModelExtended>(new BsonDocument(), new FindOptions<object, LightModelExtended>
                        {
                            //Limit = (int)channel.MaxPayedRows
                        }));

                        data = await result.ToListAsync();
                    }

                    else
                        data = await (await collection.FindAsync<LightModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                    NotificationHub.GetHubContext().Clients.All.light(data.ToArray());

                    return Ok(new ApiResponse(200, mapped));
                }
            }
            return this.BadRequest(new ApiResponse(400));
        }

        [HttpPost, Route("frustration")]
        public async Task<IHttpActionResult> Frustration(FrustrationApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                collection = mongoDb.GetCollection<object>("frustration");

                var userId = (await db.Devices.FirstAsync(x => x.UniqueId.Equals(model.DeviceId))).UserId;

                var mapped = Mapper.Map<FrustrationModel>(model);
                mapped.Created = DateTime.Now;
                mapped.UserId = userId;

                var bson = BsonSerializer.Deserialize<ExpandoObject>(BsonDocument.Parse(JsonConvert.SerializeObject(mapped)));

                await collection.InsertOneAsync(bson);

                return Ok(new ApiResponse(200, mapped));
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpGet, Route("fetch/{id:long}"), Authorize]
        public async Task<IHttpActionResult> Fetch(long id)
        {
            if (id >= 1 && id <= 6)
            {
                var userHasChannel = CurrentUser.Channels.Any(x => x.ChannelId.Equals(id));

                if (userHasChannel)
                {
                    var channel = CurrentUser.Channels.First(x => x.ChannelId.Equals(id));

                    List<object> data = null;

                    switch (id)
                    {
                        case 1:
                            collection = mongoDb.GetCollection<object>("heartrate");
                            break;
                        case 2:
                            collection = mongoDb.GetCollection<object>("fridge");
                            break;
                        case 3:
                            collection = mongoDb.GetCollection<object>("movement");
                            break;
                        case 4:
                            collection = mongoDb.GetCollection<object>("location");
                            break;
                        case 5:
                            collection = mongoDb.GetCollection<object>("light");
                            break;
                        case 6:
                            collection = mongoDb.GetCollection<object>("frustration");
                            break;
                        default:
                            break;
                    }
                    if (channel.Price > 0)
                        data = await (await collection.FindAsync<object>(new BsonDocument(), new FindOptions<object, object>
                        {
                            //Limit = (int)channel.MaxPayedRows
                        })).ToListAsync();
                    else
                        data = await (await collection.FindAsync<object>(new BsonDocument("UserId", UserId))).ToListAsync();


                    return this.Ok(new ApiResponse(200, data.ToArray()));
                }

                return this.BadRequest(new ApiResponse(400, id));
            }
            return this.NotFound(new ApiResponse(404));
        }

        [HttpGet, Route("fridge/bydate"), Authorize]
        public async Task<IHttpActionResult> FridgeByDate()
        {
            collection = mongoDb.GetCollection<object>("fridge");

            var userId = User.Identity.GetUserId();

            var user = await userManager.FindByIdAsync(userId);

            var channel = await db.Channels.FirstOrDefaultAsync(x => x.ChannelId.Equals(2) && x.UserId.Equals(userId));

            if (channel != null)
            {
                List<FridgeModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<FridgeModelExtended>(new BsonDocument(), new FindOptions<object, FridgeModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    data = await (await collection.FindAsync<FridgeModelExtended>(new BsonDocument("UserId", UserId))).ToListAsync();

                List<object> response = new List<object>();

                var distinct = data.Select(x => x.Created.ToShortDateString()).Distinct();

                foreach (var date in distinct)
                {
                    int count = 0;
                    var obj = new ExpandoObject();

                    foreach (var item in data)
                    {
                        if (item.Created.ToShortDateString().Equals(date))
                            count += 1;
                    }

                    obj.AddProp("count", count);
                    obj.AddProp("date", DateTime.Parse(date));

                    response.Add(obj);
                }

                return Ok(new ApiResponse(200, response.ToArray()));
            }
            else
            {
                return this.NotFound(new ApiResponse(404));
            }
        }

        [HttpGet, Route("movement/bydate"), Authorize]
        public async Task<IHttpActionResult> PplByDay()
        {
            collection = mongoDb.GetCollection<object>("movement");

            var userId = User.Identity.GetUserId();

            var user = await userManager.FindByIdAsync(userId);

            var channel = user.Channels.FirstOrDefault(x => x.ChannelId.Equals(3) && x.UserId.Equals(userId));

            if (channel != null)
            {
                List<MovementModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<MovementModelExtended>(new BsonDocument(), new FindOptions<object, MovementModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    data = await (await collection.FindAsync<MovementModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                List<object> response = new List<object>();

                for (int i = 1; i <= 24; i++)
                {
                    int count = 0;
                    var obj = new ExpandoObject();

                    foreach (var date in data)
                    {
                        if (i.Equals(date.Created.Hour))
                        {
                            count += 1;
                            if (((IDictionary<string, object>)obj).ContainsKey("date"))
                            {
                                ((IDictionary<string, object>)obj)["date"] = date.Created;
                            }
                            else
                            {
                                obj.AddProp("date", date.Created);
                            }
                        }
                    }
                    var dict = ((IDictionary<string, object>)obj);

                    if (dict.ContainsKey("date"))
                    {
                        obj.AddProp("hour", ((DateTime)dict["date"]).Hour);
                        obj.AddProp("count", count);

                        response.Add(obj);
                    }
                }

                return Ok(new ApiResponse(200, response.ToArray()));
            }
            else
            {
                return this.NotFound(new ApiResponse(404));
            }
        }

        [HttpGet, Route("lights"), Authorize]
        public async Task<IHttpActionResult> GetLigts()
        {
            collection = mongoDb.GetCollection<object>("light");

            var userId = User.Identity.GetUserId();

            var user = await userManager.FindByIdAsync(userId);

            var channel = user.Channels.FirstOrDefault(x => x.ChannelId.Equals(5) && x.UserId.Equals(userId));

            if (channel != null)
            {
                List<LightModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<LightModelExtended>(new BsonDocument(), new FindOptions<object, LightModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }

                else
                    data = await (await collection.FindAsync<LightModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                return Ok(new ApiResponse(200, data.ToArray()));
            }

            return this.NotFound(new ApiResponse(404));
        }

        [HttpGet, Route("map"), Authorize]
        public async Task<IHttpActionResult> GetMap()
        {
            collection = mongoDb.GetCollection<object>("location");

            var userId = User.Identity.GetUserId();

            var user = await userManager.FindByIdAsync(userId);

            var channel = user.Channels.FirstOrDefault(x => x.ChannelId.Equals(4) && x.UserId.Equals(userId));

            if (channel != null)
            {
                List<LocationModelExtended> data = null;

                if (channel.Price > 0)
                {
                    var result = (await collection.FindAsync<LocationModelExtended>(new BsonDocument(), new FindOptions<object, LocationModelExtended>
                    {
                        //Limit = (int)channel.MaxPayedRows
                    }));

                    data = await result.ToListAsync();
                }
                else
                    data = await (await collection.FindAsync<LocationModelExtended>(new BsonDocument("UserId", userId))).ToListAsync();

                return Ok(new ApiResponse(200, data.ToArray()));
            }

            return this.NotFound(new ApiResponse(404));
        }
    }
}
