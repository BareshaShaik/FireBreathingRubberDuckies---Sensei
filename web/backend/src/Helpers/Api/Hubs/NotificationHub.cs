using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Microsoft.AspNet.SignalR;

namespace src.Helpers.Api.Hubs
{
    public class NotificationHub : Hub
    {
        public string Message(string message)
        {
            return new string(message.Reverse().ToArray());
        }

        public static IHubContext GetHubContext()
        {
            return GlobalHost.ConnectionManager.GetHubContext<NotificationHub>();
        }
    }
}