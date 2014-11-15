package com.google.gwt.client.client;
  
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
  
public class DefaultPage extends Composite {
    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel topPanel = new HorizontalPanel();
    private HorizontalPanel loginPanel = new HorizontalPanel();
    private HorizontalPanel bottomPanel = new HorizontalPanel();
    private Button loginButton = new Button("Login");
      
    public DefaultPage() {
          
        // initializes main panel
        initWidget(this.mainPanel);
        
        // browse Button
        final Image browseImage = new Image("images/browse.png");
        Button browseButton = new Button();
        browseButton.getElement().appendChild(browseImage.getElement());
        topPanel.add(browseImage);
        
        browseImage.addStyleName("browseStyle");

        
        // search Button
        final Image searchImage = new Image("images/search.png");
        Button searchButton = new Button();
        searchButton.getElement().appendChild(searchImage.getElement());
        topPanel.add(searchImage);
        
        searchImage.addStyleName("searchStyle");
        
        // adds browse and search button to the top panel below logo
         
        // adds login button to its own panel
        loginPanel.add(loginButton);    // adds login button
       
        // creates about us button
        final Image aboutUsButton = new Image("images/AboutUsButton.png");
        Button aboutButton = new Button();
        aboutButton.getElement().appendChild(aboutUsButton.getElement());
        bottomPanel.add(aboutUsButton);
          
        // creates contact us button
        final Image contactUsButton = new Image("images/ContactUsButton.png");
        Button contactButton = new Button();
        contactButton.getElement().appendChild(contactUsButton.getElement());
        bottomPanel.add(contactUsButton);
         
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
         
        // bottom aligns the contact and about us panel
        bottomPanel.addStyleName("aboutAlignment");
          
        // adds logo
        Image logo = new Image("images/Petfinderlogo.png");
        mainPanel.add(logo);
  
        // Assemble Main panel
        mainPanel.add(loginPanel);
          
        // displays login panel
        RootPanel.get("loginPanel").add(loginPanel);
        RootPanel.get("topPanel").add(topPanel);
        RootPanel.get("bottomPanel").add(bottomPanel);
          
        // Listen for mouse events on the browse button.
        browseImage.addClickHandler(new ClickHandler() {
          @Override
		public void onClick(ClickEvent event) {
            browsePage();
          }
        });
         
        // Listens for mouse events on the search button
        searchImage.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              searchPage();
            }
          });
         
        // Listens for mouse events on the login button
        loginButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              loginPage();
            }
          });
    }
      
    // takes it to search page
    private void searchPage() {
    	// NLI stands for not logged in. All valid user names will be at least 5 characters long,
    	// so there are no clashes
        SearchPage searchPage = new SearchPage(null);
        mainPanel.clear();
        topPanel.clear();
        loginPanel.clear();
        bottomPanel.clear();
        mainPanel.add(searchPage);
  
}
    // takes it to browse page
    private void browsePage() {
    	// NLI stands for not logged in. All valid user names will be at least 5 characters long,
    	// so there are no clashes
        BrowsePage browsePage = new BrowsePage(null);
        mainPanel.clear();
        topPanel.clear();
        loginPanel.clear();
        bottomPanel.clear();
        mainPanel.add(browsePage);
        
}
 
    // takes it to login page
    private void loginPage() {
        LoginPage loginPage = new LoginPage();
        mainPanel.clear();
        topPanel.clear();
        loginPanel.clear();
        bottomPanel.clear();
        mainPanel.add(loginPage);
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