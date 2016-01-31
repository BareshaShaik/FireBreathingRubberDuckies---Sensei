using AutoMapper;
using Microsoft.AspNet.Identity.EntityFramework;
using src.Controllers.Api;
using src.Models;
using src.Models.Api;
using src.Models.ChannelModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace src.App_Start
{
    public static class AutoMapperConfig
    {
        public static void Configure()
        {
            Mapper.CreateMap<RegisterApiModel, User>().ForMember(x => x.Claims, opts => opts.Ignore()).ForMember(x => x.Logins, opts => opts.Ignore()).ForMember(x => x.Roles, opts => opts.Ignore()).ReverseMap();
            Mapper.CreateMap<CreateUserApiModel, User>().ForMember(x => x.Claims, opts => opts.Ignore()).ForMember(x => x.Logins, opts => opts.Ignore()).ForMember(x => x.Roles, opts => opts.Ignore()).ReverseMap();
            Mapper.CreateMap<RegisterApiModel, UserApiModel>();
            Mapper.CreateMap<User, UserApiModel>().ReverseMap().ForMember(x => x.Claims, opts => opts.Ignore()).ForMember(x => x.Logins, opts => opts.Ignore()).ForMember(x => x.Roles, opts => opts.Ignore());
            Mapper.CreateMap<User, UserEmailModel>().ReverseMap().ForMember(x => x.Claims, opts => opts.Ignore()).ForMember(x => x.Logins, opts => opts.Ignore()).ForMember(x => x.Roles, opts => opts.Ignore());
            Mapper.CreateMap<RoleApiModel, IdentityRole>().ForMember(x => x.Users, opts => opts.Ignore()).ReverseMap();
            Mapper.CreateMap<UserDevice, DeviceApiModel>().ReverseMap();
            Mapper.CreateMap<Channel, TemplateApiModel>().ReverseMap();
            Mapper.CreateMap<Tag, TagApiModel>().ReverseMap();
            Mapper.CreateMap<UserChannel, UserChannelApiModel>().ReverseMap();
            Mapper.CreateMap<HeartRateModel, HeartRateApiModel>().ReverseMap();
            Mapper.CreateMap<FridgeModel, FridgeApiModel>().ReverseMap();
            Mapper.CreateMap<MovementModel, MovementApiModel>().ReverseMap();
            Mapper.CreateMap<LocationModel, LocationApiModel>().ReverseMap();
            Mapper.CreateMap<LightModel, LightApiModel>().ReverseMap();
            Mapper.CreateMap<FrustrationModel, FrustrationApiModel>().ReverseMap();
            Mapper.CreateMap<UserChannel, UserChannelApiModel>().ReverseMap();
        }
    }
}