using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class UserDetailsApiModel
    {
        public string Status { get; set; }
        public bool Employed { get; set; }
        public bool Married { get; set; }
    }
}