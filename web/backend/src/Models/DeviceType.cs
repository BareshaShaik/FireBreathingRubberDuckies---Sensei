using Newtonsoft.Json;
using src.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace src.Models
{
    [Table("DeviceTypes")]
    public class DeviceType : AbstractModel
    {
        public DeviceType()
        {
            this.Devices = new HashSet<UserDevice>();
        }

        public string Name { get; set; }

        [JsonIgnore]
        public virtual ICollection<UserDevice> Devices { get; set; }
    }
}