using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Models.ChannelModels
{
    public class HeartRateModel
    {
        public int ChannelId { get; set; }
        public long Rate { get; set; }
        public double Lng { get; set; }
        public double Lat { get; set; }
        public string City { get; set; }
        public string UserId { get; set; }
        public DateTime Created { get; set; }
    }

    public class HeartRateModelExtended : HeartRateModel
    {
        public ObjectId _id { get; set; }
    }
}