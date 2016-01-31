using src.Controllers.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity;
using System.Threading.Tasks;
using src.Helpers.Api.Response;
using src.Helpers.Api.Results;
using AutoMapper;
using src.Models.Api;
using MongoDB.Bson;

namespace src.Controllers.Api
{
    [RoutePrefix("api/1/tags")]
    public class TagsApiController : AbstractMongoController
    {
        [HttpGet, Route("")]
        public IHttpActionResult FetchAll()
        {
            var mapped = Mapper.Map<IEnumerable<TagApiModel>>(db.Tags);

            return Ok(new ApiResponse(200, mapped.ToArray()));
        }

        [HttpPost, Route("")]
        public IHttpActionResult FetchEventsByTag(TagSearchModel model)
        {
            return Ok(new ApiResponse(200));
        }
    }
}
