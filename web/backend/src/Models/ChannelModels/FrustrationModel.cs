using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Models.ChannelModels
{
    public class FrustrationModel
    {
        public long Counter { get; set; }
        public string UserId { get; set; }
        public DateTime Created { get; set; }
    }
}