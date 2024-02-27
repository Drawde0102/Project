import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FoodOrderingSystem extends JFrame implements ActionListener 
{
    List<String> basket = new ArrayList<>();
    List<Double> basketPrice = new ArrayList<>();
    private int basketItemCount = 0;
    final private Font mainFont = new Font("Segon print", Font.BOLD, 15);
    private JPanel sidebarPanel, mainPanel;
    private JButton homeButton, BasketButton, OrdersButton, emptyButton, SpecialButton;

    //___________Basic Display, mainly the buttons and GUI stuff _______________________________________________
    public FoodOrderingSystem() 
    {
        setTitle("Page Navigation Demo");
        setMinimumSize(new Dimension(600, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout());
        sidebarPanel.setMinimumSize(new Dimension(100, 400));
        sidebarPanel.setBackground(Color.decode("#FFB417"));

        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("images\\logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBounds(10,10,100,100);
        logoPanel.setBackground(Color.decode("#FFB417"));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        sidebarPanel.add(logoPanel, BorderLayout.NORTH);


        //____ SIDEBAR BUTTONS______________________________________________________________________________
        homeButton = new JButton("Home");
        homeButton.setFont(mainFont);
        homeButton.setBounds(0, 120, 100, 30);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setOpaque(true);
        homeButton.setBackground(Color.decode("#FFB417"));
        homeButton.setForeground(Color.WHITE);
        homeButton.addActionListener(this);
        sidebarPanel.add(homeButton);

        BasketButton = new JButton("Basket");
        BasketButton.setFont(mainFont);
        BasketButton.setBounds(0, 170, 100, 30); 
        BasketButton.setBorderPainted(false);
        BasketButton.setContentAreaFilled(false);
        BasketButton.setFocusPainted(false);
        BasketButton.setOpaque(true);
        BasketButton.setBackground(Color.decode("#FFB417"));
        BasketButton.setForeground(Color.WHITE);
        BasketButton.addActionListener(this);
        sidebarPanel.add(BasketButton);

        OrdersButton = new JButton("Orders");
        OrdersButton.setFont(mainFont);
        OrdersButton.setBounds(0, 220, 100, 30); 
        OrdersButton.setBorderPainted(false);
        OrdersButton.setContentAreaFilled(false);
        OrdersButton.setFocusPainted(false);
        OrdersButton.setOpaque(true);
        OrdersButton.setBackground(Color.decode("#FFB417"));
        OrdersButton.setForeground(Color.WHITE);
        OrdersButton.addActionListener(this);
        sidebarPanel.add(OrdersButton);

        SpecialButton = new JButton("Specials");
        SpecialButton.setFont(mainFont);
        SpecialButton.setBounds(0, 270, 100, 30); 
        SpecialButton.setBorderPainted(false);
        SpecialButton.setContentAreaFilled(false);
        SpecialButton.setFocusPainted(false);
        SpecialButton.setOpaque(true);
        SpecialButton.setBackground(Color.decode("#FFB417"));
        SpecialButton.setForeground(Color.WHITE);
        SpecialButton.addActionListener(this);
        sidebarPanel.add(SpecialButton);

        emptyButton = new JButton("");// I DONT KNOW WHY, BUT THIS NEEDS TO BE HERE FOR GUI, I THINK IT PUSHUES THE OTHER BUTTONS UP SO THAT THEY ARE IN RIGHT PLACE
        emptyButton.setBounds(0,320, 100, 30); 
        emptyButton.setBorderPainted(false);
        emptyButton.setContentAreaFilled(false);
        emptyButton.setFocusPainted(false);
        emptyButton.setOpaque(false);
        emptyButton.setBackground(Color.decode("#FFB417"));
        sidebarPanel.add(emptyButton);

        //_________ MAINPANEL ______________________
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        //_______ SCROLLBAR ____________________
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() 
        {
            @Override
            protected void configureScrollBarColors() 
            {
                thumbColor = Color.decode("#9CBAC9");
            }
        });

        //___ ADD TO THE MAINPANEL___________________________________________________________________________
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidebarPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        HomePage();// DISPLAY HOME DEFAULT

        setVisible(true);
    }

    public void HomePage() 
    {
        mainPanel.removeAll();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        String[] foodNames = new String[jdbcMethods.CountRows("orderitem")];
        String[] prices = new String[jdbcMethods.CountRows("orderitem")];
        int yspace = 0;
        for(int i = 0; i < foodNames.length; i++)
        {
            foodNames[i] = "    " + jdbcMethods.getColumnData("orderitem", "ItemName", i+1);
            prices[i] = "   " + jdbcMethods.getColumnData("orderitem", "Price", i+1);
        }
    
        //_________ DISPLAY ITEMS ____________________________________________________
        for (int i = 0; i < foodNames.length; i++) 
        {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());

            JLabel nameLabel = new JLabel(foodNames[i]);
            itemPanel.add(nameLabel, BorderLayout.WEST);

            JLabel priceLabel = new JLabel(prices[i]);
            itemPanel.add(priceLabel, BorderLayout.CENTER);

            JButton addButton = new JButton("Add to Basket");
            String foodName = foodNames[i];
            double price = Double.parseDouble(prices[i]);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    basket.add(foodName);
                    basketPrice.add(price);

                    basketItemCount++;
                    System.out.println(basketItemCount);
                }
            });
            itemPanel.add(addButton, BorderLayout.EAST);
            addButton.setBounds(0, yspace+30, 100, 70);

            mainPanel.add(itemPanel, BorderLayout.CENTER);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    

    

    public void Basket() 
    {
        mainPanel.removeAll();

        JPanel basketPanel = new JPanel();
        basketPanel.setLayout(new GridLayout(basket.size(), 2));

        for (int i = 0; i < basket.size(); i++) 
        {
            JLabel nameLabel = new JLabel(basket.get(i));
            JLabel priceLabel = new JLabel("£" + String.valueOf(basketPrice.get(i)));
            basketPanel.add(nameLabel);
            basketPanel.add(priceLabel);
        }

        double totalPrice = 0;
        for (Double price : basketPrice) {
            totalPrice += price;
        }

        JLabel totalLabel = new JLabel("    Total: ");
        JLabel totalPriceLabel = new JLabel("£" + String.valueOf(totalPrice));

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(1, 2));
        totalPanel.add(totalLabel);
        totalPanel.add(totalPriceLabel);

        //________ ADD TO ORDERS TABLE ____________________________________________________________________________
        JPanel Confirm = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(basketPanel, BorderLayout.NORTH);
        mainPanel.add(totalPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
}

    public void displayPage2() 
    {
        mainPanel.removeAll();
        mainPanel.add(new JLabel("This is Page 2"), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //*********** Redirects menu option ******************************************************************************************************************* */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) 
        {
            HomePage();
        } 
        else if (e.getSource() == BasketButton) 
        {
            Basket();
        } 
        else if (e.getSource() == OrdersButton) 
        {
            displayPage2();
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new FoodOrderingSystem();
            }
        });
    }
}

