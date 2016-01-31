﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class FrustrationApiModel
    {
        [Required, Range(1, 6)]
        public long Counter { get; set; }
        [Required]
        public int ChannelId { get; set; }
        [Required]
        public double Lat { get; set; }
        [Required]
        public double Lng { get; set; }
        [Required]
        public string DeviceId { get; set; }
    }
}