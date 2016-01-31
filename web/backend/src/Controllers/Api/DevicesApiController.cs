using src.Controllers.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using src.Helpers.Api.Results;
using AutoMapper;
using System.Threading.Tasks;
using src.Models;
using src.Helpers.Api.Response;
using src.Models.Api;
using System.Data.Entity;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/devices")]
    public class DevicesApiController : AbstractApiController
    {
        [HttpGet, Route("me"), Authorize]
        public async Task<IHttpActionResult> Mine()
        {
            if (CurrentUser != null)
            {
                var userId = CurrentUser.Id;

                var devices = Mapper.Map<IEnumerable<UserDevice>>(await db.Devices.Where(x => x.UserId.Equals(userId)).Include(x => x.Type).ToListAsync());

                return Ok(new ApiResponse(200, devices));
            }
            return this.BadRequest(new ApiResponse(400, new { Message = "devices/mine failed" }));
        }

        [HttpPost, Route("pair"), Authorize]
        public async Task<IHttpActionResult> PairDevice(DeviceApiModel model)
        {
            if (ModelState.IsValid && model != null)
            {
                if (!db.Devices.Any(x => x.UniqueId.Equals(model.UniqueId) && x.TypeId.Equals(model.TypeId)))
                {
                    model.UserId = CurrentUser.Id;

                    var mapped = Mapper.Map<UserDevice>(model);

                    CurrentUser.Devices.Add(mapped);

                    await db.SaveChangesAsync();

                    return Ok(new ApiResponse(200, model));
                }
                else if (CurrentUser.Devices.Any(x => x.UniqueId.Equals(model.UniqueId) && x.TypeId.Equals(model.TypeId)))
                {
                    return Ok(new ApiResponse(200, model));
                }
            }

            return this.BadRequest(new ApiResponse(400, new { Message = "devices/pairdevice failed" }));
        }
    }
}
