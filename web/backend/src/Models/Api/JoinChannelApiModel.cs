using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class JoinChannelApiModel
    {
        [Required, Range(1, 6)]
        public long ChannelId { get; set; }
        [Required]
        public long MaxPayedRows { get; set; }
        public long Price { get; set; }
    }
}