using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Http.Controllers;

namespace src.Helpers.Api.Authentication.Tokens.Attributes
{
    public class FallbackAuthorizeAttribute : AuthorizeAttribute
    {
        public override Task OnAuthorizationAsync(HttpActionContext actionContext, CancellationToken cancellationToken)
        {
            return Task.FromResult(0);
        }
    }
}