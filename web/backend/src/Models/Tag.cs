using src.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace src.Models
{
    [Table("Tags")]
    public class Tag : AbstractModel
    {
        public string Name { get; set; }
    }
}