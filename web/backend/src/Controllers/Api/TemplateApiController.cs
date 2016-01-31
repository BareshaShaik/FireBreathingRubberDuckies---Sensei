using AutoMapper;
using src.Controllers.Abstract;
using src.Helpers.Api.Hubs;
using src.Helpers.Api.Response;
using src.Helpers.Api.Results;
using src.Models;
using src.Models.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/templates")]
    public class TemplateApiController : AbstractApiController
    {
        [HttpGet, Route("")]
        public IHttpActionResult FetchAll()
        {
            var mapped = Mapper.Map<IEnumerable<TemplateApiModel>>(db.Templates);

            return Ok(new ApiResponse(200, mapped.ToArray()));
        }

        [HttpPost, Route(""), Authorize(Roles = "Admin")]
        public async Task<IHttpActionResult> Create(Channel model)
        {
            if (ModelState.IsValid && model != null)
            {
                db.Templates.Add(model);

                await db.SaveChangesAsync();

                return Ok();
            }

            return this.BadRequest(new ApiResponse(400));
        }

        [HttpGet, Route("socket")]
        public IHttpActionResult Something()
        {
            var obj = new { email = FakeO.Internet.Email() };

            var context = NotificationHub.GetHubContext();

            context.Clients.All.ping(obj);

            return Ok(new ApiResponse(200, obj));
        }
    }
}
