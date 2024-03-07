package com.esprit.controllers.Client;

import com.esprit.controllers.Admin.CoursCardAdminController;
import com.esprit.models.NoteCours;
import com.esprit.models.avis;
import com.esprit.models.cours;
import com.esprit.services.AvisService;
import com.esprit.services.CoursService;
import com.esprit.services.MyListener;
import com.esprit.services.UserDataManager;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import javafx.stage.Stage;

public class DetailsCoursClientController implements Initializable {

    @FXML
    private TextArea Description;

    @FXML
    private TextField Nom;

    @FXML
    private GridPane grid;

    @FXML
    private ListView<avis> listview;
    private ObservableList<avis> avisObservableList;

    @FXML
    private TextField datepub;

    @FXML
    private ScrollPane scroll1;
    MyListener myListener;

    private  int userId;
    private int id_cours;
    private int selectedCoursId;

    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();
    @FXML
    private Label likeLabel;

    @FXML
    private Label dislikeLabel;
  // public setIdCoursSelected(int id){this.selectedCoursId=id;}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Assurez-vous que selectedCoursId est correctement initialisé
        selectedCoursId = UserDataManager.getInstance().getSelectedCoursId();

        // Créer une instance de votre service AvisService
        AvisService avisService = new AvisService();

        // Récupérer la liste d'avis à partir de votre service
        List<avis> aviss = avisService.getByIDCours(selectedCoursId);

        // Convertir la liste en une liste observable
        avisObservableList = FXCollections.observableArrayList(aviss);

        // Définir la liste observable comme la source de données de votre ListView
        listview.setItems(avisObservableList);

        // Définir un cell factory personnalisé pour afficher les avis dans le ListView
        listview.setCellFactory(param -> new ListCell<avis>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            private final HBox content = new HBox();
            private final AvisService avisService = new AvisService();

            {
                content.setSpacing(10);
                deleteButton.setOnAction(event -> {
                    avis item = getItem();
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this comment?", ButtonType.YES, ButtonType.NO);
                    confirmDeleteAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            avisService.supprimer(item.getId());
                            listview.getItems().remove(item);
                        }
                    });
                });

                updateButton.setOnAction(event -> {
                    avis item = getItem();
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Update Comment");

                    ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

                    TextField commentInput = new TextField(item.getCommentaire());
                    dialog.getDialogPane().setContent(commentInput);

                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == updateButtonType) {
                            return commentInput.getText();
                        }
                        return null;
                    });

                    dialog.showAndWait().ifPresent(newComment -> {
                        item.setCommentaire(newComment);
                        avisService.modifier(item);
                        listview.refresh();
                    });
                });
            }

            @Override
            protected void updateItem(avis item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    System.out.println(UserDataManager.getInstance().getUserId());
                    boolean canEdit = UserDataManager.getInstance().getUserId() == item.getIdu();
                    System.out.println(item.getIdu());
                    System.out.println(canEdit);
                    Label commentaireLabel = new Label(item.getCommentaire());
                    content.getChildren().clear();
                    content.getChildren().add(commentaireLabel);

                    if (canEdit) {

                        content.getChildren().addAll(updateButton, deleteButton);
                    }

                    setGraphic(content);
                }
            }

        });

        // Mettre à jour la vue
        Update();
    }
    public void exportToPDF() {
        String path = "comments.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Set up font
            PdfFont font = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);

            // Add a title to the document
            Paragraph title = new Paragraph("Course Comments Summary")
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Likes and Dislikes summary
            Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
            summaryTable.addCell(new Cell().setBackgroundColor(ColorConstants.LIGHT_GRAY).add(new Paragraph("Likes")));
            summaryTable.addCell(new Cell().add(new Paragraph(likeLabel.getText())));
            summaryTable.addCell(new Cell().setBackgroundColor(ColorConstants.LIGHT_GRAY).add(new Paragraph("Dislikes")));
            summaryTable.addCell(new Cell().add(new Paragraph(dislikeLabel.getText())));
            document.add(summaryTable);

            // Comments Section
            document.add(new Paragraph("\nComments:").setFont(font).setFontSize(14));

            for (avis avis : avisObservableList) {
                Paragraph comment = new Paragraph("Comment: " + avis.getCommentaire())
                        .setMarginLeft(20)
                        .setFirstLineIndent(20)
                        .setMarginBottom(10);
                document.add(comment);
            }

            document.close();
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleExportToPDF() {
        exportToPDF();
    }
    private void setChosenCours(cours cours) {
        selectedCours = cours;
        Nom.setText(cours.getNom());
        Description.setText(cours.getDescription());
        datepub.setText(String.valueOf(cours.getDate_pub()));

        }
    public void Update() {
        CoursService cs = new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        selectedCoursId = UserDataManager.getInstance().getSelectedCoursId();
        coursList.addAll(cs.getByIDUser(selectedCoursId));
        updateLikesAndDislikes() ;
        myListener = new MyListener() {
            @Override
            public void onClickListener(cours cours) {
                setChosenCours(cours);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (cours ev : coursList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Admin/CoursCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CoursCardAdminController evvvcontroller = fxmlLoader.getController();
                evvvcontroller.setData(ev, myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(-20);
        scroll1.setFitToWidth(true);
        scroll1.setContent(grid);
        grid.requestLayout();
    }

    private void updateLikesAndDislikes() {
        CoursService cs= new CoursService();
        int[] counts = cs.getLikesAndDislikes(selectedCoursId);
        likeLabel.setText("Likes: " + counts[0]);
        dislikeLabel.setText("Dislikes: " + counts[1]);
    }
    @FXML
    void retuuurn(MouseEvent  event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/AfficheCoursClient.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();
    }

}
