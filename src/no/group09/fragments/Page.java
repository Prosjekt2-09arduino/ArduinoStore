package no.group09.fragments;

/**
 * Names for the pages in the shop
 *
 */
public enum Page {

	ALL,
	TOPHITS, 
	
	GAMES_ALL, 
	GAMES_MOST_POPULAR, 
	
	MEDICAL_ALL,
	MEDICAL_MOST_POPULAR,
	
	TOOLS_ALL, 
	TOOLS_MOST_POPULAR, 
	
	MEDIA_ALL,
	MEDIA_MOST_POPULAR;
	
	/**
	 * 
	 * @param s - the category as string
	 * @param page - which of the two fragment pages it is
	 * @return
	 */
	public static Page getType(String s, int page){
		
		if(s.toUpperCase().equals("GAMES")){
			switch(page){
			case 1 : return GAMES_ALL;
			case 2 : return GAMES_MOST_POPULAR;
			default : return null;
			}
		}
		else if(s.toUpperCase().equals("MEDICAL")){
			switch(page){
			case 1 : return MEDICAL_ALL;
			case 2 : return MEDICAL_MOST_POPULAR;
			default : return null;
			}	
		}
		else if(s.toUpperCase().equals("TOOLS")){
			switch(page){
			case 1 : return TOOLS_ALL;
			case 2 : return TOOLS_MOST_POPULAR;
			default : return null;
			}
		}
		else if(s.toUpperCase().equals("MEDIA")){
			switch(page){
			case 1 : return MEDIA_ALL;
			case 2 : return MEDIA_MOST_POPULAR;
			default : return null;
			}
		}
		else{
			switch(page){
			case 1 : return ALL;
			case 2 : return TOPHITS;
			default : return null;
			}
		}
	}
	/**
	 * 
	 * @param Page page
	 * @return String with what category a page belongs to
	 */
	public static String getCategoryFromType(Page page){
		switch(page){
		case GAMES_ALL: return "Games";
		case MEDICAL_ALL: return "Medical";
		case TOOLS_ALL: return "Tools";
		case MEDIA_ALL: return "Media";
		default: return "All";
		}
	}
}
