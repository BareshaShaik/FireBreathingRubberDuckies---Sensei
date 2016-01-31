using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace src.Models.Api
{
    public class UserApiModel
    {
        [Required]
        public string Id { get; set; }
        [Required]
        public string FirstName { get; set; }
        [Required]
        public string LastName { get; set; }
        [Required]
        public string UserName { get; set; }
        [Required]
        public string Email { get; set; }
        [Required]
        public string Avatar { get; set; }
        [Required]
        public string Cover { get; set; }
        public bool Enabled { get; set; }
        public string RegistrationId { get; set; }
        public string Status { get; set; }
        public bool Employed { get; set; }
        public bool Married { get; set; }
        public double Money { get; set; }
    }
}