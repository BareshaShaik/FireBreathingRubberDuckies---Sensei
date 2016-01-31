namespace src.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.UserChannels",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ChannelId = c.Long(nullable: false),
                        MaxPayedRows = c.Long(nullable: false),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => new { t.UserId, t.ChannelId }, unique: true, name: "uniqueUserChannel");
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        FirstName = c.String(),
                        LastName = c.String(),
                        RegistrationId = c.String(),
                        Avatar = c.String(),
                        Enabled = c.Boolean(nullable: false),
                        Cover = c.String(),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                        Email = c.String(maxLength: 256),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "dbo.UserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.UserDevice",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        UniqueId = c.String(nullable: false),
                        Name = c.String(nullable: false),
                        TypeId = c.Long(nullable: false),
                        UserId = c.String(maxLength: 128),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.DeviceTypes", t => t.TypeId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.UserId)
                .Index(t => t.TypeId)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.DeviceTypes",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        Name = c.String(),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.UserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.UserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        RoleId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.Roles", t => t.RoleId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "dbo.Roles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
            CreateTable(
                "dbo.Tags",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        Name = c.String(),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Channels",
                c => new
                    {
                        Id = c.Long(nullable: false, identity: true),
                        Name = c.String(),
                        Created = c.DateTime(),
                        Updated = c.DateTime(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.UserRoles", "RoleId", "dbo.Roles");
            DropForeignKey("dbo.UserRoles", "UserId", "dbo.Users");
            DropForeignKey("dbo.UserLogins", "UserId", "dbo.Users");
            DropForeignKey("dbo.UserDevice", "UserId", "dbo.Users");
            DropForeignKey("dbo.UserDevice", "TypeId", "dbo.DeviceTypes");
            DropForeignKey("dbo.UserClaims", "UserId", "dbo.Users");
            DropForeignKey("dbo.UserChannels", "UserId", "dbo.Users");
            DropIndex("dbo.Roles", "RoleNameIndex");
            DropIndex("dbo.UserRoles", new[] { "RoleId" });
            DropIndex("dbo.UserRoles", new[] { "UserId" });
            DropIndex("dbo.UserLogins", new[] { "UserId" });
            DropIndex("dbo.UserDevice", new[] { "UserId" });
            DropIndex("dbo.UserDevice", new[] { "TypeId" });
            DropIndex("dbo.UserClaims", new[] { "UserId" });
            DropIndex("dbo.Users", "UserNameIndex");
            DropIndex("dbo.UserChannels", "uniqueUserChannel");
            DropTable("dbo.Channels");
            DropTable("dbo.Tags");
            DropTable("dbo.Roles");
            DropTable("dbo.UserRoles");
            DropTable("dbo.UserLogins");
            DropTable("dbo.DeviceTypes");
            DropTable("dbo.UserDevice");
            DropTable("dbo.UserClaims");
            DropTable("dbo.Users");
            DropTable("dbo.UserChannels");
        }
    }
}
