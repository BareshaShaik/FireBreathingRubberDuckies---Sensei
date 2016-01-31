namespace src.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UserDetails : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Users", "Status", c => c.String());
            AddColumn("dbo.Users", "Employed", c => c.Boolean(nullable: false));
            AddColumn("dbo.Users", "Married", c => c.Boolean(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Users", "Married");
            DropColumn("dbo.Users", "Employed");
            DropColumn("dbo.Users", "Status");
        }
    }
}
