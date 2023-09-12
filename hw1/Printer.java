package hw1;

/**
 * Printer class that simulates a simple printer and its functions.
 * @author Tristan Sayasit
 *
 */
public class Printer {
	
	/**
	 * Number of pages assigned to a document.
	 */
	private int pagesInDoc;
	
	/**
	 * Variable that is assigned with the value of the next page in a document.
	 */
	private int nextPage;
	
	/**
	 * Holds value of total pages that have been printed by printer.
	 */
	private int totalPages;
	
	/**
	 * Holds value of number of pages left to print.
	 */
	private int pagesLeft;
	
	/**
	 * Holds number of sheets in printer tray. Regardless if tray is inserted or not.
	 */
	private int sheetsInTray;
	
	/**
	 * Number of sheets available to be used when printing. This includes when the tray is removed from the printer itself.
	 */
	private int sheetsAvailable;
	
	/**
	 * Maximum number of sheets of paper that the printer tray can hold.
	 */
	private int trayCap;
	
	/**
	 * Constructs a new printer with the given maximum tray capacity of the number of paper sheets it 
	 * can hold. Initially the tray is empty and the printer has not printed any pages. 
	 * @param trayCapacity
	 */
	public Printer(int trayCapacity) {
		
		nextPage = 0;
		totalPages = 0;
		pagesLeft = 0;
		sheetsInTray = 0;
		sheetsAvailable = 0;
		trayCap = trayCapacity;
		
	}
	
	/**
	 * Starts a new print job to make copies of a document that is a specified page length (documentPages). 
	 * Updates the next page to print as page 0 (denotes the first page of the document). 
	 * @param documentPages
	 */
	public void startPrintJob(int documentPages) {
		
		pagesInDoc = documentPages;
		nextPage = 0;
		
	}
	
	/**
	 * Returns the number of sheets available for printing. 
	 * @return
	 * Number of sheets in the tray.
	 */
	public int getSheetsAvailable() {
		
		return sheetsInTray;
		
	}
	
	/**
	 * Returns the next page number of the document that will be printed.
	 * @return
	 * Next page number of document.
	 */
	public int getNextPage() {
		
		return nextPage;
		
	}
	
	/**
	 * Returns the count of all pages printed by the printer since its construction.
	 * @return
	 * Number of pages printed since printer creation.
	 */
	public int getTotalPages() {
		
		return totalPages;
		
	}
	
	/**
	 * Simulates the printer printing a page. The number pages printed is either one or zero depending on 
	 * whether there is at least one sheet of paper available to the printer. Increments the total page count 
	 * of the printer by the number of pages printed. Advances the next page to print by the number of pages 
	 * printed (possibly wrapping around to page zero). The number of pages available to the printer and in the tray 
	 * are also updated accordingly. 
	 */
	public void printPage() {
		
		nextPage += Math.min(sheetsInTray, 1);
		pagesLeft = pagesInDoc - nextPage;
		nextPage = Math.min(pagesInDoc * pagesLeft, nextPage);
		totalPages += Math.min(sheetsInTray, 1);
		sheetsInTray = Math.max(0, sheetsInTray-1);
		
	}
	
	/**
	 * Removes the paper tray from the printer; that is, makes the sheets available to the printer zero.
	 */
	public void removeTray() {
		
		sheetsAvailable = sheetsInTray;
		sheetsInTray = 0;
		
	}
	
	/**
	 * Replaces the tray in the printer; that is, makes the sheets available to the printer 
	 * the same as the number of sheets in the tray.
	 */
	public void replaceTray() {
		
		sheetsInTray += sheetsAvailable;
		sheetsAvailable = 0;
		
	}
	
	/**
	 * Simulates removing the tray, adding the given number of sheets (up to the maximum capacity of the tray), 
	 * and replacing the tray in the printer. 
	 * @param sheets
	 */
	public void addPaper(int sheets) {
		
		this.replaceTray();
		sheetsInTray = Math.min(sheetsInTray + sheets, trayCap);
		
	}
	
	/**
	 * Simulates removing the tray, removing the given number of sheets (but not allowing the sheets to go below zero), 
	 * and replacing the tray in the printer.
	 * @param sheets
	 */
	public void removePaper(int sheets) {
		
		this.replaceTray();
		sheetsInTray = Math.max(sheetsInTray - sheets, 0);
		
	}
}