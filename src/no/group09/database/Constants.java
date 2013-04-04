package no.group09.database;

import no.group09.database.entity.App;
import no.group09.database.entity.BinaryFile;
import no.group09.database.entity.Developer;
import no.group09.database.entity.Requirements;

/**
 * Constants for use in the database.
 * Strings which contain the extended SQL queries
 */
public class Constants {

	/** Select an app from the database */
    protected final static String SELECT_APP = "select * from app where appid=?";
    protected final static String SELECT_DEVELOPER = "select * from developer where developerid=?";
    protected final static String SELECT_REQUIREMENTS = "select * from requirements where requirementid=?";
    protected final static String SELECT_PICTURES = "select * from pictures where pictureid=?";
    protected final static String SELECT_BINARYFILES = "select * from binaryfiles where appid=?"; //FIXME:working?
    
    /** Insert an app to the database */
    protected final static String INSERT_APP = "insert or replace into app (name, rating, developerid, category, description, requirementid) values (?, ?, ?, ?, ?, ?)";
    protected final static String INSERT_DEVELOPER = "insert or replace into developer (name, website) values (?, ?)";
    protected final static String INSERT_REQUIREMENTS = "insert or replace into requirements (name, description, compatible) values (?, ?, ?)";
    protected final static String INSERT_PICTURES = "insert or replace into pictures (appid, fileURL) values (?, ?)";
    protected final static String INSERT_BINARYFILES = "insert or replace into binaryfiles (appid, file) values (?, ?)"; //FIXME:working?

    /** Delete an app from the database */
    protected final static String DELETE_APP = "delete from app where appid=?";
    protected final static String DELETE_DEVELOPER = "delete from developer where developerid=?";
    protected final static String DELETE_REQUIREMENTS = "delete from requirements where requirementid=?";
    protected final static String DELETE_PICTURES = "delete from pictures where pictureid=?";
    protected final static String DELETE_BINARYFILES = "delete from binaryfiles where appid=?"; //FIXME:working?
	
	/** app(appid, name, description, developerid, icon) */
	protected final static String APP_TABLE="app";
	protected final static String APP_ID="appid";
	protected final static String APP_NAME="name"; 
	protected final static String APP_RATING="rating"; 
	protected final static String APP_DEVELOPERID="developerid"; 
	protected final static String APP_CATEGORY="category"; 
	protected final static String APP_DESCRIPTION="description"; 
	protected final static String APP_REQUIREMENTID="requirementid"; 
	
	/** binaryfile(appid, file) */
	protected final static String BINARYFILES_TABLE = "binaryfiles";
	protected final static String BINARYFILES_ID = "binaryfileid";
	protected final static String BINARYFILES_APPID = "appid";
	protected final static String BINARYFILES_FILE = "file";
	
	/** developer(developerid, name, website) */
	protected final static String DEVELOPER_TABLE="developer";
	protected final static String DEVELOPER_ID="developerid";
	protected final static String DEVELOPER_NAME="name";
	protected final static String DEVELOPER_WEBSITE="website";
	
	/** pictures(pictureid, appid, fileURL) */
	protected final static String PICTURES_TABLE="pictures";
	protected final static String PICTURES_ID="pictureid";
	protected final static String PICTURES_APPID="appid";
	protected final static String PICTURES_FILEURL="fileURL";
	
	/** requirement(requirement, name, description) */
	protected final static String REQUIREMENTS_TABLE="requirements";
	protected final static String REQUIREMENTS_ID="requirementid";
	protected final static String REQUIREMENTS_NAME="name";
	protected final static String REQUIREMENTS_DESCRIPTION="description";
	protected final static String REQUIREMENTS_COMPATIBLE="compatible";
	
	/** appusespins(appid, requirementid) */
	protected final static String APPUSESPINS_TABLE="appusespins";
	protected final static String APPUSESPINS_APPID="appid";
	protected final static String APPUSESPINS_REQUIREMENTID="requirementid";
	
	/**	Database app creation sql statement */
	protected static final String DATABASE_CREATE_APP = 
			"CREATE TABLE IF NOT EXISTS app (" +
					"appid integer primary key autoincrement, " +
					"name varchar(160), " +
					"rating int(10), " +
					"developerid int(10)," +
					"category varchar(200)," +
					"description varchar(200)," +
					"requirementid int(10))";

	/** Database binaryfiles creation sql statement */
	protected final static String DATABASE_CREATE_BINARYFILES =
			"CREATE TABLE binaryfiles (" +
					"binaryfileid integer primary key autoincrement, " + 
					"appid int(10), " +
					"file BLOB(1000000) )";	//FIXME:working?
	
	/** Database developer creation sql statement */
	protected static final String DATABASE_CREATE_DEVELOPER = 
			"CREATE TABLE developer (" +
					"developerid integer primary key autoincrement, " +
					"name varchar(160), " +
					"website varchar(200))";
	
	/** Database pictures creation sql statement */
	protected static final String DATABASE_CREATE_PICTURES = 
			"CREATE TABLE pictures (" +
					"pictureid integer primary key autoincrement, " +
					"appid int(10), " +
					"fileURL varchar(200))";
	
	/** Database requirement creation sql statement */
	protected static final String DATABASE_CREATE_REQUIREMENTS = 
			"CREATE TABLE requirements (" +
					"requirementid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200)," +
					"compatible varchar(10))";
	
	/** Database appUsesPins creation sql statement */
	protected static final String DATABASE_CREATE_APPUSESPINS = 
				"CREATE TABLE appUsesPins (" +
						"appid int(10) , " +
						"requirementid int(10)" +
						"FOREIGN KEY (appid) REFERENCES app(appid)," +
						"FOREIGN KEY (requirementid) REFERENCES requirements(requirementid))";

	/**
	 * Populates the database with some hardcoded examples
	 * @param save - the current save class
	 * @param useContentProvider - if you want to use content provider or not
	 */
	public static void populateDatabase(Save save) {
			//App(name, rating, developerid, category, description, requirementid)
			save.insertApp(new App("FunGame", 3, 1, "Games", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("Game", 4, 2, "Games", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("PlayTime", 2, 5, "Games", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("FunTime", 4, 4, "Games", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("PlayWithPlayers", 1, 2, "Games", "This describes this amazing, life changing app. yey!", 3));	
			
			save.insertApp(new App("Medic", 1, 1, "Medical", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("Medical", 6, 3, "Medical", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("Helper", 4, 5, "Medical", "This describes this amazing, life changing app. yey!", 1));	
			
			save.insertApp(new App("Tool", 5, 5, "Tools", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("ToolBox", 5, 3, "Tools", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("BoxTooler", 2, 1, "Tools", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("ToolTooler", 3, 1, "Tools", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("ScrewDriver", 4, 1, "Tools", "This describes this amazing, life changing app. yey!", 2));	
			
			save.insertApp(new App("Player", 4, 5, "Media", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("MusicP", 2, 2, "Media", "This describes this amazing, life changing app. yey!", 3));
			
			save.insertDeveloper(new Developer("Wilhelm", "www.lol.com"));
			save.insertDeveloper(new Developer("Robin", "www.haha.com"));
			save.insertDeveloper(new Developer("Jeppe", "www.hehe.com"));
			save.insertDeveloper(new Developer("Bjørn", "www.hoho.com"));
			save.insertDeveloper(new Developer("Ståle", "www.rofl.com"));
			save.insertDeveloper(new Developer("Nina", "www.kake.com"));
			
			save.insertRequirements(new Requirements("name", "description", true));
			save.insertRequirements(new Requirements("name", "desc..", true));
			save.insertRequirements(new Requirements("name", "desc..", false));
			
			save.insertBinaryFile(new BinaryFile(1, Constants.getByteExample()));
			save.insertBinaryFile(new BinaryFile(2, Constants.getByteExample()));
			save.insertBinaryFile(new BinaryFile(3, Constants.getByteExample()));
	}
	
	/** returns a example binary file */
	public static byte[] getByteExample(){
		String s = ":100000000C9461000C947E000C947E000C947E0095" +
					":100010000C947E000C947E000C947E000C947E0068" +
					":100020000C947E000C947E000C947E000C947E0058" +
					":100030000C947E000C947E000C947E000C947E0048" +
					":100040000C9490000C947E000C947E000C947E0026" +
					":100050000C947E000C947E000C947E000C947E0028" +
					":100060000C947E000C947E00000000002400270009" +
					":100070002A0000000000250028002B0000000000DE" +
					":1000800023002600290004040404040404040202DA" +
					":100090000202020203030303030301020408102007" +
					":1000A0004080010204081020010204081020000012" +
					":1000B0000007000201000003040600000000000029" +
					":1000C000000011241FBECFEFD8E0DEBFCDBF11E08E" +
					":1000D000A0E0B1E0E0E2F4E002C005900D92A030B3" +
					":1000E000B107D9F711E0A0E0B1E001C01D92A9303D" +
					":1000F000B107E1F70E94FF010C940E020C9400007E" +
					":10010000089584E061E00E946C016CE271E080E09F" +
					":1001100090E00E94D80084E060E00E94AB01089566" +
					":100120001F920F920FB60F9211242F933F938F932C" +
					":100130009F93AF93BF938091040190910501A0918B" +
					":100140000601B0910701309108010196A11DB11D72" +
					":10015000232F2D5F2D3720F02D570196A11DB11DA6" +
					":10016000209308018093040190930501A093060158" +
					":10017000B09307018091000190910101A0910201CB" +
					":10018000B09103010196A11DB11D809300019093D0" +
					":100190000101A0930201B0930301BF91AF919F9120" +
					":1001A0008F913F912F910F900FBE0F901F90189538" +
					":1001B0009B01AC017FB7F8948091000190910101FF" +
					":1001C000A0910201B091030166B5A89B05C06F3FE5" +
					":1001D00019F00196A11DB11D7FBFBA2FA92F982F2D" +
					":1001E0008827860F911DA11DB11D62E0880F991F00" +
					":1001F000AA1FBB1F6A95D1F7BC012DC0FFB7F894A9" +
					":100200008091000190910101A0910201B091030140" +
					":10021000E6B5A89B05C0EF3F19F00196A11DB11DE1" +
					":10022000FFBFBA2FA92F982F88278E0F911DA11DD0" +
					":10023000B11DE2E0880F991FAA1FBB1FEA95D1F7F5" +
					":10024000861B970B885E9340C8F221503040404097" +
					":10025000504068517C4F211531054105510571F61B" +
					":100260000895789484B5826084BD84B5816084BD2E" +
					":1002700085B5826085BD85B5816085BDEEE6F0E01F" +
					":10028000808181608083E1E8F0E01082808182607B" +
					":100290008083808181608083E0E8F0E080818160FC" +
					":1002A0008083E1EBF0E0808184608083E0EBF0E02C" +
					":1002B000808181608083EAE7F0E0808184608083D0" +
					":1002C000808182608083808181608083808180687A" +
					":1002D00080831092C1000895CF93DF93482F50E0A0" +
					":1002E000CA0186569F4FFC0134914A575F4FFA016D" +
					":1002F0008491882369F190E0880F991FFC01E859E7" +
					":10030000FF4FA591B491FC01EE58FF4FC591D491D8" +
					":10031000662351F42FB7F8948C91932F90958923ED" +
					":100320008C93888189230BC0623061F42FB7F894D5" +
					":100330008C91932F909589238C938881832B88832C" +
					":100340002FBF06C09FB7F8948C91832B8C939FBFCF" +
					":10035000DF91CF910895482F50E0CA0182559F4FF9" +
					":10036000FC012491CA0186569F4FFC0194914A5783" +
					":100370005F4FFA013491332309F440C0222351F135" +
					":10038000233071F0243028F42130A1F0223011F50F" +
					":1003900014C02630B1F02730C1F02430D9F404C0A5" +
					":1003A000809180008F7703C0809180008F7D809343" +
					":1003B000800010C084B58F7702C084B58F7D84BD66" +
					":1003C00009C08091B0008F7703C08091B0008F7D0D" +
					":1003D0008093B000E32FF0E0EE0FFF1FEE58FF4FC9" +
					":1003E000A591B4912FB7F894662321F48C91909540" +
					":1003F000892302C08C91892B8C932FBF0895CF93B2" +
					":10040000DF930E9431010E948100C0E0D0E00E9491" +
					":1004100080002097E1F30E940000F9CFF894FFCF0D" +
					":00000001FF";    
		byte[] bytes = s.getBytes();
		return bytes;
	}
}
