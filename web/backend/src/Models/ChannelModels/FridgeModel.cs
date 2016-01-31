using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Models.ChannelModels
{
    public class FridgeModel
    {
        public int ChannelId { get; set; }
        public double Lng { get; set; }
        public double Lat { get; set; }
        public string City { get; set; }
        public string UserId { get; set; }
        public DateTime Created { get; set; }
    }

    public class FridgeModelExtended : FridgeModel
    {
        public ObjectId _id { get; set; }
    }
}