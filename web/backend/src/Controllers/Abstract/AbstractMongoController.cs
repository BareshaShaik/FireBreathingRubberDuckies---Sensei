using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Controllers.Abstract
{
    public abstract class AbstractMongoController : AbstractApiController
    {
        protected MongoClient mongoClient { get; set; }
        protected IMongoCollection<object> collection { get; set; }
        protected IMongoDatabase mongoDb { get; set; }

        public AbstractMongoController()
        {
            this.mongoClient = new MongoClient("mongodb://vas_admin:vas@ds035995.mongolab.com:35995/vas");
            this.mongoDb = this.mongoClient.GetDatabase("vas");
            //this.collection = this.mongoDb.GetCollection<object>("event_data");
        }
    }
}