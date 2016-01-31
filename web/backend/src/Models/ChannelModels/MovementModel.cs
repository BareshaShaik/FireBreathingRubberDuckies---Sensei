using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Models.ChannelModels
{
    public class MovementModel
    {
        public int ChannelId { get; set; }
        public long Steps { get; set; }
        public double Lng { get; set; }
        public double Lat { get; set; }
        public string City { get; set; }
        public string UserId { get; set; }
        public DateTime Created { get; set; }
        public DateTime Updated { get; set; }
    }

    public class MovementModelExtended : MovementModel
    {
        public ObjectId _id { get; set; }
    }
}