using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class DeviceApiModel
    {
        public long Id { get; set; }
        [Required]
        public string UniqueId { get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public long TypeId { get; set; }
        public string UserId { get; set; }
    }
}