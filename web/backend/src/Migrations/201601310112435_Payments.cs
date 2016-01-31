namespace src.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Payments : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.UserChannels", "Price", c => c.Double(nullable: false));
            AddColumn("dbo.Users", "Money", c => c.Double(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Users", "Money");
            DropColumn("dbo.UserChannels", "Price");
        }
    }
}
