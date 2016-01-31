using src.Helpers.Database.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using EntityFramework.Triggers;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using src.Models.Abstract;

namespace src.Models
{
    public class UserChannel : AbstractModel
    {
        [Index("uniqueUserChannel", IsUnique = true, Order = 0), Required]
        public string UserId { get; set; }
        public virtual User User { get; set; }
        [Index("uniqueUserChannel", IsUnique = true, Order = 1), Required]
        public long ChannelId { get; set; }
        [Required]
        public long MaxPayedRows { get; set; }
        public double Price { get; set; }
    }
}