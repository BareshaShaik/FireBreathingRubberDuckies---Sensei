using AutoMapper;
using src.Controllers.Abstract;
using src.Helpers.Api.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/types")]
    public class DeviceTypeApiController : AbstractApiController
    {
        [HttpGet, Route("")]
        public IHttpActionResult FetchAll()
        {
            var types = db.DeviceTypes;

            return Ok(new ApiResponse(200, types.ToArray()));
        }
    }
}
