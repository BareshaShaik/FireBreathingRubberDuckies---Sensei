using Newtonsoft.Json;
using src.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace src.Models
{
    [Table("UserDevice")]
    public class UserDevice : AbstractModel
    {
        [Required]
        public string UniqueId { get; set; }
        [Required]
        public string Name { get; set; }

        [Required, JsonIgnore]
        public long TypeId { get; set; }
        public virtual DeviceType Type { get; set; }

        public string UserId { get; set; }
        [ForeignKey("UserId"), JsonIgnore]
        public virtual User User { get; set; }
    }
}