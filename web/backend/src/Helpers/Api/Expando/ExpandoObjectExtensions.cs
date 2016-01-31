using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Web;

namespace src.Helpers.Api.Expando
{
    public static class ExpandoObjectExtensions
    {
        public static void AddProp(this ExpandoObject current, string key, object value)
        {
            ((IDictionary<string, object>)current).Add(key, value);
        }
    }
}