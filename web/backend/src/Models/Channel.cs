using src.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace src.Models
{
    [Table("Channels")]
    public class Channel : AbstractModel
    {
        public string Name { get; set; }
    }
}