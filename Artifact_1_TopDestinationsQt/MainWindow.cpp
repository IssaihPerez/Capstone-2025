#include <QApplication>
#include <QMainWindow>
#include <QListWidget>
#include <QLabel>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QDesktopServices>
#include <QUrl>
#include <QVector>
#include <QPixmap>

// Main Window that user will see and use
class MainWindow : public QMainWindow {
public:
    explicit MainWindow(QWidget* parent = nullptr);

private:
    struct Destination {
        QString name;
        QString blurb;
        QUrl url;
        QString imagePath;   // Added for image support
    };

    // Widgets
    QListWidget* list_ = nullptr;
    QLabel* title_ = nullptr;
    QLabel* blurb_ = nullptr;
    QPushButton* openBtn_ = nullptr;
    QLabel* footer_ = nullptr;
    QLabel* imageLabel_ = nullptr;

    QVector<Destination> data_;

    void populate();
    void showDestination(int index);
};

MainWindow::MainWindow(QWidget* parent) : QMainWindow(parent) {
    auto* central = new QWidget(this);
    setCentralWidget(central);
    resize(900, 600);

    // Widgets
    list_ = new QListWidget(central);
    title_ = new QLabel("<b>Select a destination</b>", central);
    blurb_ = new QLabel(central);
    blurb_->setWordWrap(true);
    openBtn_ = new QPushButton("Open Link", central);
    footer_ = new QLabel("Developer: Issaih Perez", central);
    imageLabel_ = new QLabel(central);
    imageLabel_->setFixedSize(200, 150);
    imageLabel_->setScaledContents(true);

    // Right-hand layout
    auto* right = new QVBoxLayout();
    right->addWidget(title_);
    right->addWidget(blurb_);
    right->addWidget(openBtn_);
    right->addWidget(imageLabel_);   // Image now included here
    right->addStretch();
    right->addWidget(footer_);

    // Root layout
    auto* root = new QHBoxLayout(central);
    root->addWidget(list_, 1);
    root->addLayout(right, 2);

    // Fill data
    data_ = {
        {"Norway",    "Enjoy a wonderful environment.",
                      QUrl("https://www.tripadvisor.com/Tourism-g190455-Norway"), "images/NorwayPic.jpg"},
        {"Alaska",    "Breathe the fresh, wild air.",
                      QUrl("https://www.alaska.org/"), "images/AlaskaPic.jpg"},
        {"Michigan",  "Come enjoy the lakes and towns.",
                      QUrl("https://www.michigan.org/"), "images/MichiganPic.jpg"},
        {"Tennessee", "Explore the trails and music.",
                      QUrl("https://www.tnvacation.com/"), "images/TennesseePic.jpg"},
        {"Minnesota", "Celebrate your time off.",
                      QUrl("https://www.exploreminnesota.com/"), "images/MinnesotaPic.jpg"}
    };

    populate();

    // Connect list selection to showDestination
    QObject::connect(list_, &QListWidget::currentRowChanged, this, [this](int row) {
        showDestination(row);
        });

    // Connect button once
    QObject::connect(openBtn_, &QPushButton::clicked, this, [this] {
        int i = list_->currentRow();
        if (i >= 0 && i < data_.size() && data_[i].url.isValid())
            QDesktopServices::openUrl(data_[i].url);
        });

    if (!data_.isEmpty()) list_->setCurrentRow(0);
}

void MainWindow::populate() {
    list_->clear();
    for (const auto& d : data_) {
        auto* item = new QListWidgetItem(d.name, list_);
        item->setToolTip(d.blurb);
        list_->addItem(item);
    }
}

void MainWindow::showDestination(int index) {
    if (index < 0 || index >= data_.size()) return;

    const Destination& destination = data_[index];

    // Title
    title_->setText("<b>" + destination.name + "</b>");

    // Blurb
    blurb_->setText(destination.blurb);

    // Image
    QPixmap pixmap(destination.imagePath);
    if (!pixmap.isNull()) {
        imageLabel_->setPixmap(pixmap.scaled(
            imageLabel_->size(),
            Qt::KeepAspectRatio,
            Qt::SmoothTransformation
        ));
    }
    else {
        imageLabel_->setText("[Image not found]");
    }
}

int main(int argc, char* argv[]) {
    QApplication app(argc, argv);
    MainWindow w;
    w.setWindowTitle("Top 5 Destinations List"); // Title of the Window/Executable
    w.show();
    return app.exec();
}