import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destinations List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}


class TopDestinationListFrame extends JFrame {
    private DefaultListModel listModel;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);

        listModel = new DefaultListModel();

        //Make updates to your top 5 list below. Import the new image files to resources directory.
        //Published By: TripAdvisor https://www.tripadvisor.com/Tourism-g190455-Norway-Vacations.html
        addDestinationNameAndPicture("1. Norway:\nEnjoy a wonderful environment full of lovely scenery and welcoming locals", new ImageIcon(getClass().getResource("/resources/a-noreuga-fica-no-norte.jpg")));
        //Published By: Afar https://www.afar.com/travel-guides/united-states/alaska/guide
        addDestinationNameAndPicture("2. Alaska:\nTake in the wonderful, breathtaking beauty of Alaska and see their well renowned Northern Lights", new ImageIcon(getClass().getResource("/resources/afar.brightspotcdn.jpg")));
        //Published By: Pure Michigan https://www.michigan.org/article/trip-idea/find-your-island-style-michigan
        addDestinationNameAndPicture("3. Michigan:\nCome to Michigan and enjoy their 'Sea of Serenity' which is well known to calm the senses", new ImageIcon(getClass().getResource("/resources/DJI_0976_Mackinac-Video_SIp-n-Sail_Drone.jpg")));
        //Published By: iStock https://www.istockphoto.com/essential/photo/great-smoky-mountains-national-park-scenic-sunrise-landscape-at-oconaluftee-gm163642367-22806567
        addDestinationNameAndPicture("4. Tennessee:\nExplore Tennessee's woods and discover why so many people never want to leave", new ImageIcon(getClass().getResource("/resources/istockphoto-163642367-612x612.jpg")));
        //Published By: Redfin https://www.redfin.com/blog/what-is-minnesota-known-for/
        addDestinationNameAndPicture("5. Minnesota:\nCelebrate your time off with the beautiful sighs that Minnesota has to offer", new ImageIcon(getClass().getResource("/resources/minneapolis-mnGettyImages-155374567.jpg")));
        
        JList list = new JList(listModel);
        //Changes the color of the background
        list.setBackground(Color.cyan);
        JScrollPane scrollPane = new JScrollPane(list);

        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(2);

        list.setCellRenderer(renderer);
        
        //Sets a label for my name
        JLabel nameLabel = new JLabel("Developer: Issaih Perez");
        
        getContentPane().add(nameLabel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addDestinationNameAndPicture(String text, Icon icon) {
        TextAndIcon tai = new TextAndIcon(text, icon);
        listModel.addElement(tai);
    }
}


class TextAndIcon {
    private String text;
    private Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}


class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
    int index, boolean isSelected, boolean hasFocus) {
        // The object from the combo box model MUST be a TextAndIcon.
        TextAndIcon tai = (TextAndIcon) value;

        // Sets text and icon on 'this' JLabel.
        setText(tai.getText());
        setIcon(tai.getIcon());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    // The following methods are overridden to be empty for performance
    // reasons. If you want to understand better why, please read:
    //
    // http://java.sun.com/javase/6/docs/api/javax/swing/DefaultListCellRenderer.html#override

    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}