using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class TagApiModel
    {
        public string Name { get; set; }
    }

    public class TagSearchModel
    {
        [Required]
        public string HashTag { get; set; }
    }
}