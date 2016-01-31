using MongoDB.Driver;
using src.Controllers.Abstract;
using src.Models.Api;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Data.Entity;
using src.Helpers.Api.Response;
using src.Helpers.Api.Results;
using MongoDB.Bson;
using src.Helpers.Api.Authentication.Tokens.Attributes;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity;
using Newtonsoft.Json;
using MongoDB.Bson.Serialization;
using System.Dynamic;
using src.Helpers.Api.Models.Enums;
using src.Models.ChannelModels;
using Newtonsoft.Json.Linq;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/data")]
    public class DataApiController : AbstractMongoController
    {
        /*[HttpPost, Route("store")]
        public async Task<IHttpActionResult> Store(DataApiModel model)
        {
            

            return Ok(new ApiResponse(200));
        }

        [HttpGet, Route("fetch/{id:long}"), FallbackAuthorize]
        public async Task<IHttpActionResult> Fetch(long id)
        {
            return Ok(new ApiResponse(200));
        }*/
    }
}
