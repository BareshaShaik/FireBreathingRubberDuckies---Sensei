using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class UserChannelApiModel
    {
        //public UserApiModel User { get; set; }
        public long ChannelId { get; set; }
        public long MaxPayedRows { get; set; }
    }
}