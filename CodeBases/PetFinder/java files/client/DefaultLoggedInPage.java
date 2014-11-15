package com.google.gwt.client.client;
 
import com.google.gwt.client.shared.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
 
public class DefaultLoggedInPage extends Composite {
    private VerticalPanel mainPanel = new VerticalPanel(); 
    private HorizontalPanel topPanel = new HorizontalPanel(); 
    private HorizontalPanel logoutPanel = new HorizontalPanel();
    private HorizontalPanel bottomPanel = new HorizontalPanel();
    private FlexTable buttonsFlexTable = new FlexTable(); 
    private Button browseButton = new Button(); 
    private Button searchButton = new Button(); 
    private Button adminButton = new Button("Admin Panel");
    private Button myPetButton = new Button("My Pets");
    private Button logoutButton = new Button("Log out");
    private Button aboutButton = new Button();
    private Button contactButton = new Button();
    private User user;
    
     
    public DefaultLoggedInPage(User user) {
    	
    	this.user = user;
         Label welcomeLabel = new Label("Welcome " + user.getUsername());
        // initializes main panel 
        initWidget(this.mainPanel);
        
        // =======================  all images here  =================================
        
        // Set up all the images required
        Image logo = new Image("images/Petfinderlogo.png");
        Image searchImage = new Image("images/search.png");
        Image browseImage = new Image("images/browse.png");
        Image facebookImage = new Image("images/facebook.png");
        Image twitterImage = new Image("images/twitter.png");
        Image aboutUsButton = new Image("images/AboutUsButton.png");
        Image contactUsButton = new Image("images/ContactUsButton.png");
        
        
        // ======================== anchors and buttons ===================================
        
        // facebook anchor
        Anchor facebookAnchor = new Anchor("Facebook", true, 
        		"https://www.facebook.com/sharer/sharer.php?u=" + "http://petfindertestapp.appspot.com/", "_blank");
        
        // twitter anchor
        Anchor twitterAnchor = new Anchor("Twitter", true, 
        		"https://twitter.com/share?url=" + "http://petfindertestapp.appspot.com/", "_blank");
        
        
        // create aboutButton with aboutUsButton.png
        aboutButton.getElement().appendChild(aboutUsButton.getElement());

         
        // create contactButton with contactUsButton.png
        contactButton.getElement().appendChild(contactUsButton.getElement());
        
        // create searchButton with search.png
        searchButton.getElement().appendChild(searchImage.getElement());
        
        // create browseButton with browse.png
        browseButton.getElement().appendChild(browseImage.getElement());

        

        // ======================== Layout ==============================
        
        // Assemble Main Panel
        // 1. adds Petfinderlogo
        // 2. adds buttonsFlexTable
        mainPanel.add(logo);
        mainPanel.add(buttonsFlexTable);
        
        // Assemble things inside the buttonsFlexTable
        // 1. browseButton and searchButton
        // 2. Facebook
        // 3. Twitter

        buttonsFlexTable.setWidget(1, 3, browseImage);
        buttonsFlexTable.setWidget(1, 4, searchImage);
        buttonsFlexTable.setWidget(2, 1, facebookImage);
        buttonsFlexTable.setWidget(2, 2, facebookAnchor);
        buttonsFlexTable.setWidget(3, 1, twitterImage);
        buttonsFlexTable.setWidget(3, 2, twitterAnchor);
        
//        // topPanel is not used for this page         
//        // assemble top panel with browseButton and search Button
//        topPanel.add(browseButton);     // adds browse button
//        topPanel.add(searchButton);     // adds search button
//        

        // assemble logoutPanel 
        logoutPanel.add(welcomeLabel);
        if (user.isAdmin())
        logoutPanel.add(adminButton);
        logoutPanel.add(myPetButton);
        logoutPanel.add(logoutButton);    // adds logout button
     
        // assemble bottomPanel
        bottomPanel.add(aboutUsButton); 
        bottomPanel.add(contactUsButton);
         
        // bottom aligns the contact and about us panel 
        bottomPanel.addStyleName("aboutAlignment");
         

        // ==================== get style from PetFinder.html ===========================
         
        // displays login panel
        RootPanel.get().add(logoutPanel);
        RootPanel.get("loginPanel").add(logoutPanel);
        //RootPanel.get("topPanel").add(topPanel);
        RootPanel.get("bottomPanel").add(bottomPanel);
        
        
        // ================ clickHandlers are all here ====================
         
        // Listen for mouse events on the browseButton.
        browseImage.addClickHandler(new ClickHandler() {
          @Override
		public void onClick(ClickEvent event) {
            browsePage();
          }
        });
         
        // Listen for mouse events on the searchButton.
        searchImage.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              searchPage();
            }
          });

        // Listen for mouse events on the logoutButton.
        logoutButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              defaultPage();
            }
          });
        
        myPetButton.addClickHandler(new ClickHandler() {
        	@Override
			public void onClick(ClickEvent event){
        		userControlPanel();
        	}
        });
        
        adminButton.addClickHandler(new ClickHandler() {
        	@Override
			public void onClick(ClickEvent event){
        		adminPanel();
        	}
        });
        
        // creates popup when you click on about us button
        aboutUsButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              new AboutPopup().show();
            }
        });
        
        // creates popup when you click on contact us button
        contactUsButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              new ContactPopup().show();
            }
        });
         
 
    }
     
    // takes it to search page 
    private void searchPage() {
        SearchPage searchPage = new SearchPage(user); 
        mainPanel.clear();
        topPanel.clear(); 
        logoutPanel.clear();
        bottomPanel.clear(); 
        mainPanel.add(searchPage);
 
}
    // takes it to browse page
    private void browsePage() {
        BrowsePage browsePage = new BrowsePage(user); 
        mainPanel.clear();
        topPanel.clear(); 
        logoutPanel.clear();
        bottomPanel.clear(); 
        mainPanel.add(browsePage);
       
}
     
    // Logs out user by taking them to defaultPage
    private void defaultPage() {
        DefaultPage defaultPage = new DefaultPage(); 
        mainPanel.clear();
        topPanel.clear(); 
        logoutPanel.clear();
        bottomPanel.clear();
        mainPanel.add(defaultPage);
      }
    
    // Go to User Control Panel
    private void userControlPanel(){
    	UserControlPanel userControlPanel = new UserControlPanel(user);
    	mainPanel.clear();
    	topPanel.clear(); 
    	logoutPanel.clear();
    	bottomPanel.clear();
    	mainPanel.add(userControlPanel);
    }
    
    // Go to Admin Panel
    private void adminPanel(){
    	AdminPanel adminPanel = new AdminPanel(user);
    	mainPanel.clear();
    	topPanel.clear(); 
    	logoutPanel.clear();
    	bottomPanel.clear();
    	mainPanel.add(adminPanel);
    }
    
    public static class AboutPopup extends PopupPanel {
    	 
        public AboutPopup() {
          // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
          // If this is set, the panel closes itself automatically when the user
          // clicks outside of it.
          super(true);
          setGlassEnabled(true);
          // PopupPanel is a SimplePanel, so you have to set it's widget property to
          // whatever you want its contents to be.
          setWidget(new HTML(new SafeHtmlBuilder().appendEscapedLines("We are four UBC computer science students."
                  + "\nThis pet finder is our project for Computer Science 310."
                  + "\n\n\n Click outside the box to exit.").toSafeHtml()));
        }
      }
       
      public static class ContactPopup extends PopupPanel {
   
          public ContactPopup() {
            // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
            // If this is set, the panel closes itself automatically when the user
            // clicks outside of it.
            super(true);
            setGlassEnabled(true);
            // PopupPanel is a SimplePanel, so you have to set it's widget property to
            // whatever you want its contents to be
            setWidget(new HTML(new SafeHtmlBuilder().appendEscapedLines("Contact Information:"
                  + "\nYongzhe Wang - yongzhewang@live.ca"
                  + "\nSudihugeng Hardjojo - sudihugeng@gmail.com"
                  + "\nParham Rahmani - parham.rahmani@gmail.com"
                  + "\nYushu Lin - justicelemon@hotmail.com"
                  + "\n\n\n Click outside the box to exit. ").toSafeHtml()));
   
          }
      }
}