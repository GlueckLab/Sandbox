package edu.ucdenver.bios.bugtracking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.ucdenver.bios.bugtracking.shared.EmailContent;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BugTrackingSvc implements EntryPoint
{

    private VerticalPanel mainPanel = new VerticalPanel();
    
    private Button sendEmailButton = new Button("Send Email");
    
    private Button clear = new Button("Clear");
    
    private SendEmailServiceAsync sendEmailSvc = (SendEmailServiceAsync)GWT.create(SendEmailService.class);
   
    
    Label nameOfTheBugLabel = new Label();
    Label priorityOfTheBugLabel = new Label();
    Label descriptionLabel = new Label();
    Label summaryLabel = new Label();
    
    Grid grid = new Grid(5,2);
    HorizontalPanel headingHP = new HorizontalPanel();
    Label heading = new Label();
    
    ListBox priorityListBox = new ListBox();

    
    TextBox nameOfTheBugTB = new TextBox();
    TextBox priorityOftheBugTB = new TextBox();
    TextBox summaryTB = new TextBox();
    TextArea descriptionTA = new TextArea();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {

        heading.setText("Report a Bug");
        headingHP.add(heading);
        
        
        nameOfTheBugLabel.setText("Bug Name");
        grid.setWidget(0, 0, nameOfTheBugLabel);
        grid.setWidget(0, 1, nameOfTheBugTB);
        
        summaryLabel.setText("Summary");
        grid.setWidget(1, 0, summaryLabel);
        summaryTB.setWidth("175%");
        grid.setWidget(1, 1, summaryTB);
        
        priorityOfTheBugLabel.setText("Priority");
        
        priorityListBox.addItem("Major", "1");
        priorityListBox.addItem("Blocker", "2");
        priorityListBox.addItem("Critical", "3");
        priorityListBox.addItem("Minor", "4");
        priorityListBox.addItem("Trival", "5");
        
        grid.setWidget(2, 0, priorityOfTheBugLabel);
        grid.setWidget(2, 1, priorityListBox);
        
        descriptionLabel.setText("Description");
        grid.setWidget(3, 0, descriptionLabel);
        grid.setWidget(3, 1, descriptionTA);
        
        
        grid.setWidget(4, 0, sendEmailButton);
        grid.setWidget(4, 1, clear);
        
        mainPanel.add(headingHP);
        mainPanel.add(grid);
        
        sendEmailButton.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
              EmailContent emailContent = onSendEmail();
              @SuppressWarnings("rawtypes")
            AsyncCallback callback = new AsyncCallback() {
                
                @Override
                public void onFailure(Throwable caught) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onSuccess(Object result) {
                    // TODO Auto-generated method stub
                    
                }
            };
              try {
                  System.out.println("Captured Data");
                  sendEmailSvc.sendEmail(emailContent, callback);
                  System.out.println("Called Server Side Implementation");
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });
        
        clear.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
             onClearClick();
              
          }
      });
        RootPanel.get("email").add(mainPanel);
        
        nameOfTheBugTB.setFocus(true);

    }
    
    public EmailContent onSendEmail()
    {
        EmailContent emailContent = new EmailContent();
        
        emailContent.setBugName(nameOfTheBugTB.getText());
        emailContent.setSubject(summaryTB.getText());
        emailContent.setDescription(descriptionTA.getText());
        int index = priorityListBox.getSelectedIndex();
        emailContent.setPriority(priorityListBox.getItemText(index));
        return emailContent;
    }
    
    public void onClearClick()
    {
        nameOfTheBugTB.setText(null);
        priorityListBox.setSelectedIndex(0);
        descriptionTA.setText(null);
        summaryTB.setText(null);
    }
}
