package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gui.supportclass.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import services.*;
import services.impl.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

import entities.*;

public class PrototypeController implements Initializable {


	DateTimeFormatter dateformatter;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		trainticketsystem_service = ServiceManager.createTrainTicketSystem();
		thirdpartyservices_service = ServiceManager.createThirdPartyServices();
		buyticketservice_service = ServiceManager.createBuyTicketService();
		queryorderservice_service = ServiceManager.createQueryOrderService();
		manageroutecrudservice_service = ServiceManager.createManageRouteCRUDService();
		managetraincrudservice_service = ServiceManager.createManageTrainCRUDService();
		manageticketcrudservice_service = ServiceManager.createManageTicketCRUDService();
		manageordercrudservice_service = ServiceManager.createManageOrderCRUDService();
		manageaccoutcrudservice_service = ServiceManager.createManageAccoutCRUDService();
		manageseatcrudservice_service = ServiceManager.createManageSeatCRUDService();
		modifytripservice_service = ServiceManager.createModifyTripService();
		deleteoverdueorderservice_service = ServiceManager.createDeleteOverdueOrderService();
				
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	   	 //prepare data for contract
	   	 prepareData();
	   	 
	   	 //generate invariant panel
	   	 genereateInvairantPanel();
	   	 
		 //Actor Threeview Binding
		 actorTreeViewBinding();
		 
		 //Generate
		 generatOperationPane();
		 genereateOpInvariantPanel();
		 
		 //prilimariry data
		 try {
			DataFitService.fit();
		 } catch (PreconditionException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
		 
		 //generate class statistic
		 classStatisicBingding();
		 
		 //generate object statistic
		 generateObjectTable();
		 
		 //genereate association statistic
		 associationStatisicBingding();

		 //set listener 
		 setListeners();
	}
	
	/**
	 * deepCopyforTreeItem (Actor Generation)
	 */
	TreeItem<String> deepCopyTree(TreeItem<String> item) {
		    TreeItem<String> copy = new TreeItem<String>(item.getValue());
		    for (TreeItem<String> child : item.getChildren()) {
		        copy.getChildren().add(deepCopyTree(child));
		    }
		    return copy;
	}
	
	/**
	 * check all invariant and update invariant panel
	 */
	public void invairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}				
			}
			
			for (Entry<String, Label> inv : service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * check op invariant and update op invariant panel
	 */		
	public void opInvairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : op_entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
			for (Entry<String, Label> inv : op_service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	*	generate op invariant panel 
	*/
	public void genereateOpInvariantPanel() {
		
		opInvariantPanel = new HashMap<String, VBox>();
		op_entity_invariants_label_map = new LinkedHashMap<String, Label>();
		op_service_invariants_label_map = new LinkedHashMap<String, Label>();
		
		VBox v;
		List<String> entities;
		v = new VBox();
		
		//entities invariants
		entities = ManageRouteCRUDServiceImpl.opINVRelatedEntity.get("createRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageRouteCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageRouteCRUDServiceImpl.opINVRelatedEntity.get("queryRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageRouteCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageRouteCRUDServiceImpl.opINVRelatedEntity.get("modifyRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageRouteCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageRouteCRUDServiceImpl.opINVRelatedEntity.get("deleteRoute");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteRoute" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageRouteCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteRoute", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTrainCRUDServiceImpl.opINVRelatedEntity.get("createTrain");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createTrain" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTrainCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createTrain", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTrainCRUDServiceImpl.opINVRelatedEntity.get("queryTrain");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryTrain" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTrainCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryTrain", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTrainCRUDServiceImpl.opINVRelatedEntity.get("modifyTrain");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyTrain" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTrainCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyTrain", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTrainCRUDServiceImpl.opINVRelatedEntity.get("deleteTrain");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteTrain" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTrainCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteTrain", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTicketCRUDServiceImpl.opINVRelatedEntity.get("createTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTicketCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTicketCRUDServiceImpl.opINVRelatedEntity.get("queryTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTicketCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTicketCRUDServiceImpl.opINVRelatedEntity.get("modifyTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTicketCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageTicketCRUDServiceImpl.opINVRelatedEntity.get("deleteTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageTicketCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("createOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("queryOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("modifyOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("deleteOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageAccoutCRUDServiceImpl.opINVRelatedEntity.get("createAccout");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createAccout" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageAccoutCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createAccout", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageAccoutCRUDServiceImpl.opINVRelatedEntity.get("queryAccout");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryAccout" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageAccoutCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryAccout", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageAccoutCRUDServiceImpl.opINVRelatedEntity.get("modifyAccout");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyAccout" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageAccoutCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyAccout", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageAccoutCRUDServiceImpl.opINVRelatedEntity.get("deleteAccout");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteAccout" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageAccoutCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteAccout", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSeatCRUDServiceImpl.opINVRelatedEntity.get("createSeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createSeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSeatCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createSeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSeatCRUDServiceImpl.opINVRelatedEntity.get("querySeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("querySeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSeatCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("querySeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSeatCRUDServiceImpl.opINVRelatedEntity.get("modifySeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifySeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSeatCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifySeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSeatCRUDServiceImpl.opINVRelatedEntity.get("deleteSeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteSeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSeatCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteSeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BuyTicketServiceImpl.opINVRelatedEntity.get("buyTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("buyTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BuyTicketService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("buyTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BuyTicketServiceImpl.opINVRelatedEntity.get("saveOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("saveOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BuyTicketService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("saveOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BuyTicketServiceImpl.opINVRelatedEntity.get("showSeats");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("showSeats" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BuyTicketService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("showSeats", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BuyTicketServiceImpl.opINVRelatedEntity.get("selectSeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("selectSeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BuyTicketService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("selectSeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = BuyTicketServiceImpl.opINVRelatedEntity.get("pay");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("pay" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("BuyTicketService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("pay", v);
		
		v = new VBox();
		
		//entities invariants
		entities = TrainTicketSystemImpl.opINVRelatedEntity.get("queryInformation");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryInformation" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("TrainTicketSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryInformation", v);
		
		v = new VBox();
		
		//entities invariants
		entities = TrainTicketSystemImpl.opINVRelatedEntity.get("cancelTrip");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("cancelTrip" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("TrainTicketSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("cancelTrip", v);
		
		v = new VBox();
		
		//entities invariants
		entities = QueryOrderServiceImpl.opINVRelatedEntity.get("queryByAccoutId");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryByAccoutId" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("QueryOrderService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryByAccoutId", v);
		
		v = new VBox();
		
		//entities invariants
		entities = QueryOrderServiceImpl.opINVRelatedEntity.get("queryTicketId");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryTicketId" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("QueryOrderService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryTicketId", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ModifyTripServiceImpl.opINVRelatedEntity.get("updateTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("updateTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ModifyTripService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("updateTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ModifyTripServiceImpl.opINVRelatedEntity.get("updateOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("updateOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ModifyTripService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("updateOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ModifyTripServiceImpl.opINVRelatedEntity.get("showSeatsByRouteId");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("showSeatsByRouteId" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ModifyTripService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("showSeatsByRouteId", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ModifyTripServiceImpl.opINVRelatedEntity.get("selectNewSeat");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("selectNewSeat" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ModifyTripService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("selectNewSeat", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ModifyTripServiceImpl.opINVRelatedEntity.get("payDifference");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("payDifference" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ModifyTripService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("payDifference", v);
		
		v = new VBox();
		
		//entities invariants
		entities = DeleteOverdueOrderServiceImpl.opINVRelatedEntity.get("deleteOverdueTicket");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteOverdueTicket" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("DeleteOverdueOrderService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteOverdueTicket", v);
		
		v = new VBox();
		
		//entities invariants
		entities = DeleteOverdueOrderServiceImpl.opINVRelatedEntity.get("deleteOverdueOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteOverdueOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("DeleteOverdueOrderService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteOverdueOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ThirdPartyServicesImpl.opINVRelatedEntity.get("sendNotification");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("sendNotification" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ThirdPartyServices")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("sendNotification", v);
		
		
	}
	
	
	/*
	*  generate invariant panel
	*/
	public void genereateInvairantPanel() {
		
		service_invariants_label_map = new LinkedHashMap<String, Label>();
		entity_invariants_label_map = new LinkedHashMap<String, Label>();
		
		//entity_invariants_map
		VBox v = new VBox();
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			Label l = new Label(inv.getKey());
			l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			service_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		//entity invariants
		for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
			
			String INVname = inv.getKey();
			Label l = new Label(INVname);
			if (INVname.contains("AssociationInvariants")) {
				l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #099b17 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			} else {
				l.setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
			}	
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			entity_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		ScrollPane scrollPane = new ScrollPane(v);
		scrollPane.setFitToWidth(true);
		all_invariant_pane.setMaxHeight(850);
		
		all_invariant_pane.setContent(scrollPane);
	}	
	
	
	
	/* 
	*	mainPane add listener
	*/
	public void setListeners() {
		 mainPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			 
			 	if (newTab.getText().equals("System State")) {
			 		System.out.println("refresh all");
			 		refreshAll();
			 	}
		    
		    });
	}
	
	
	//checking all invariants
	public void checkAllInvariants() {
		
		invairantPanelUpdate();
	
	}	
	
	//refresh all
	public void refreshAll() {
		
		invairantPanelUpdate();
		classStatisticUpdate();
		generateObjectTable();
	}
	
	
	//update association
	public void updateAssociation(String className) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber();
		}
		
	}
	
	public void updateAssociation(String className, int index) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber(index);
		}
		
	}	
	
	public void generateObjectTable() {
		
		allObjectTables = new LinkedHashMap<String, TableView>();
		
		TableView<Map<String, String>> tableRoute = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableRoute_RouteId = new TableColumn<Map<String, String>, String>("RouteId");
		tableRoute_RouteId.setMinWidth("RouteId".length()*10);
		tableRoute_RouteId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RouteId"));
		    }
		});	
		tableRoute.getColumns().add(tableRoute_RouteId);
		TableColumn<Map<String, String>, String> tableRoute_StartStation = new TableColumn<Map<String, String>, String>("StartStation");
		tableRoute_StartStation.setMinWidth("StartStation".length()*10);
		tableRoute_StartStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("StartStation"));
		    }
		});	
		tableRoute.getColumns().add(tableRoute_StartStation);
		TableColumn<Map<String, String>, String> tableRoute_EndStation = new TableColumn<Map<String, String>, String>("EndStation");
		tableRoute_EndStation.setMinWidth("EndStation".length()*10);
		tableRoute_EndStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EndStation"));
		    }
		});	
		tableRoute.getColumns().add(tableRoute_EndStation);
		TableColumn<Map<String, String>, String> tableRoute_Time = new TableColumn<Map<String, String>, String>("Time");
		tableRoute_Time.setMinWidth("Time".length()*10);
		tableRoute_Time.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Time"));
		    }
		});	
		tableRoute.getColumns().add(tableRoute_Time);
		TableColumn<Map<String, String>, String> tableRoute_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
		tableRoute_TrainId.setMinWidth("TrainId".length()*10);
		tableRoute_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
		    }
		});	
		tableRoute.getColumns().add(tableRoute_TrainId);
		
		//table data
		ObservableList<Map<String, String>> dataRoute = FXCollections.observableArrayList();
		List<Route> rsRoute = EntityManager.getAllInstancesOf("Route");
		for (Route r : rsRoute) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getRouteId() != null)
				unit.put("RouteId", String.valueOf(r.getRouteId()));
			else
				unit.put("RouteId", "");
			if (r.getStartStation() != null)
				unit.put("StartStation", String.valueOf(r.getStartStation()));
			else
				unit.put("StartStation", "");
			if (r.getEndStation() != null)
				unit.put("EndStation", String.valueOf(r.getEndStation()));
			else
				unit.put("EndStation", "");
			if (r.getTime() != null)
				unit.put("Time", String.valueOf(r.getTime()));
			else
				unit.put("Time", "");
			if (r.getTrainId() != null)
				unit.put("TrainId", String.valueOf(r.getTrainId()));
			else
				unit.put("TrainId", "");

			dataRoute.add(unit);
		}
		
		tableRoute.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableRoute.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Route", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableRoute.setItems(dataRoute);
		allObjectTables.put("Route", tableRoute);
		
		TableView<Map<String, String>> tableTrain = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableTrain_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
		tableTrain_TrainId.setMinWidth("TrainId".length()*10);
		tableTrain_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
		    }
		});	
		tableTrain.getColumns().add(tableTrain_TrainId);
		TableColumn<Map<String, String>, String> tableTrain_Name = new TableColumn<Map<String, String>, String>("Name");
		tableTrain_Name.setMinWidth("Name".length()*10);
		tableTrain_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableTrain.getColumns().add(tableTrain_Name);
		TableColumn<Map<String, String>, String> tableTrain_TrainType = new TableColumn<Map<String, String>, String>("TrainType");
		tableTrain_TrainType.setMinWidth("TrainType".length()*10);
		tableTrain_TrainType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TrainType"));
		    }
		});	
		tableTrain.getColumns().add(tableTrain_TrainType);
		
		//table data
		ObservableList<Map<String, String>> dataTrain = FXCollections.observableArrayList();
		List<Train> rsTrain = EntityManager.getAllInstancesOf("Train");
		for (Train r : rsTrain) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getTrainId() != null)
				unit.put("TrainId", String.valueOf(r.getTrainId()));
			else
				unit.put("TrainId", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			unit.put("TrainType", String.valueOf(r.getTrainType()));

			dataTrain.add(unit);
		}
		
		tableTrain.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableTrain.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Train", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableTrain.setItems(dataTrain);
		allObjectTables.put("Train", tableTrain);
		
		TableView<Map<String, String>> tableTicket = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableTicket_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
		tableTicket_TicketId.setMinWidth("TicketId".length()*10);
		tableTicket_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
		    }
		});	
		tableTicket.getColumns().add(tableTicket_TicketId);
		TableColumn<Map<String, String>, String> tableTicket_RouteId = new TableColumn<Map<String, String>, String>("RouteId");
		tableTicket_RouteId.setMinWidth("RouteId".length()*10);
		tableTicket_RouteId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RouteId"));
		    }
		});	
		tableTicket.getColumns().add(tableTicket_RouteId);
		TableColumn<Map<String, String>, String> tableTicket_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
		tableTicket_SeatId.setMinWidth("SeatId".length()*10);
		tableTicket_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
		    }
		});	
		tableTicket.getColumns().add(tableTicket_SeatId);
		TableColumn<Map<String, String>, String> tableTicket_Price = new TableColumn<Map<String, String>, String>("Price");
		tableTicket_Price.setMinWidth("Price".length()*10);
		tableTicket_Price.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Price"));
		    }
		});	
		tableTicket.getColumns().add(tableTicket_Price);
		TableColumn<Map<String, String>, String> tableTicket_IsValid = new TableColumn<Map<String, String>, String>("IsValid");
		tableTicket_IsValid.setMinWidth("IsValid".length()*10);
		tableTicket_IsValid.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsValid"));
		    }
		});	
		tableTicket.getColumns().add(tableTicket_IsValid);
		
		//table data
		ObservableList<Map<String, String>> dataTicket = FXCollections.observableArrayList();
		List<Ticket> rsTicket = EntityManager.getAllInstancesOf("Ticket");
		for (Ticket r : rsTicket) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getTicketId() != null)
				unit.put("TicketId", String.valueOf(r.getTicketId()));
			else
				unit.put("TicketId", "");
			if (r.getRouteId() != null)
				unit.put("RouteId", String.valueOf(r.getRouteId()));
			else
				unit.put("RouteId", "");
			if (r.getSeatId() != null)
				unit.put("SeatId", String.valueOf(r.getSeatId()));
			else
				unit.put("SeatId", "");
			unit.put("Price", String.valueOf(r.getPrice()));
			unit.put("IsValid", String.valueOf(r.getIsValid()));

			dataTicket.add(unit);
		}
		
		tableTicket.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableTicket.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Ticket", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableTicket.setItems(dataTicket);
		allObjectTables.put("Ticket", tableTicket);
		
		TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableOrder_OrderId = new TableColumn<Map<String, String>, String>("OrderId");
		tableOrder_OrderId.setMinWidth("OrderId".length()*10);
		tableOrder_OrderId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OrderId"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_OrderId);
		TableColumn<Map<String, String>, String> tableOrder_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
		tableOrder_TicketId.setMinWidth("TicketId".length()*10);
		tableOrder_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_TicketId);
		TableColumn<Map<String, String>, String> tableOrder_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
		tableOrder_AccoutId.setMinWidth("AccoutId".length()*10);
		tableOrder_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_AccoutId);
		TableColumn<Map<String, String>, String> tableOrder_CreateTime = new TableColumn<Map<String, String>, String>("CreateTime");
		tableOrder_CreateTime.setMinWidth("CreateTime".length()*10);
		tableOrder_CreateTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CreateTime"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_CreateTime);
		TableColumn<Map<String, String>, String> tableOrder_OrderStatus = new TableColumn<Map<String, String>, String>("OrderStatus");
		tableOrder_OrderStatus.setMinWidth("OrderStatus".length()*10);
		tableOrder_OrderStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OrderStatus"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_OrderStatus);
		
		//table data
		ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
		List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
		for (Order r : rsOrder) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getOrderId() != null)
				unit.put("OrderId", String.valueOf(r.getOrderId()));
			else
				unit.put("OrderId", "");
			if (r.getTicketId() != null)
				unit.put("TicketId", String.valueOf(r.getTicketId()));
			else
				unit.put("TicketId", "");
			if (r.getAccoutId() != null)
				unit.put("AccoutId", String.valueOf(r.getAccoutId()));
			else
				unit.put("AccoutId", "");
			if (r.getCreateTime() != null)
				unit.put("CreateTime", String.valueOf(r.getCreateTime()));
			else
				unit.put("CreateTime", "");
			unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));

			dataOrder.add(unit);
		}
		
		tableOrder.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableOrder.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Order", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableOrder.setItems(dataOrder);
		allObjectTables.put("Order", tableOrder);
		
		TableView<Map<String, String>> tableAccout = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableAccout_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
		tableAccout_AccoutId.setMinWidth("AccoutId".length()*10);
		tableAccout_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
		    }
		});	
		tableAccout.getColumns().add(tableAccout_AccoutId);
		TableColumn<Map<String, String>, String> tableAccout_Name = new TableColumn<Map<String, String>, String>("Name");
		tableAccout_Name.setMinWidth("Name".length()*10);
		tableAccout_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableAccout.getColumns().add(tableAccout_Name);
		TableColumn<Map<String, String>, String> tableAccout_PhoneNumber = new TableColumn<Map<String, String>, String>("PhoneNumber");
		tableAccout_PhoneNumber.setMinWidth("PhoneNumber".length()*10);
		tableAccout_PhoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PhoneNumber"));
		    }
		});	
		tableAccout.getColumns().add(tableAccout_PhoneNumber);
		
		//table data
		ObservableList<Map<String, String>> dataAccout = FXCollections.observableArrayList();
		List<Accout> rsAccout = EntityManager.getAllInstancesOf("Accout");
		for (Accout r : rsAccout) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getAccoutId() != null)
				unit.put("AccoutId", String.valueOf(r.getAccoutId()));
			else
				unit.put("AccoutId", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getPhoneNumber() != null)
				unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
			else
				unit.put("PhoneNumber", "");

			dataAccout.add(unit);
		}
		
		tableAccout.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableAccout.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Accout", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableAccout.setItems(dataAccout);
		allObjectTables.put("Accout", tableAccout);
		
		TableView<Map<String, String>> tableSeat = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableSeat_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
		tableSeat_SeatId.setMinWidth("SeatId".length()*10);
		tableSeat_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
		    }
		});	
		tableSeat.getColumns().add(tableSeat_SeatId);
		TableColumn<Map<String, String>, String> tableSeat_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
		tableSeat_TrainId.setMinWidth("TrainId".length()*10);
		tableSeat_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
		    }
		});	
		tableSeat.getColumns().add(tableSeat_TrainId);
		TableColumn<Map<String, String>, String> tableSeat_SeatType = new TableColumn<Map<String, String>, String>("SeatType");
		tableSeat_SeatType.setMinWidth("SeatType".length()*10);
		tableSeat_SeatType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SeatType"));
		    }
		});	
		tableSeat.getColumns().add(tableSeat_SeatType);
		
		//table data
		ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
		List<Seat> rsSeat = EntityManager.getAllInstancesOf("Seat");
		for (Seat r : rsSeat) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getSeatId() != null)
				unit.put("SeatId", String.valueOf(r.getSeatId()));
			else
				unit.put("SeatId", "");
			if (r.getTrainId() != null)
				unit.put("TrainId", String.valueOf(r.getTrainId()));
			else
				unit.put("TrainId", "");
			unit.put("SeatType", String.valueOf(r.getSeatType()));

			dataSeat.add(unit);
		}
		
		tableSeat.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableSeat.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Seat", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableSeat.setItems(dataSeat);
		allObjectTables.put("Seat", tableSeat);
		

		
	}
	
	/* 
	* update all object tables with sub dataset
	*/ 
	public void updateRouteTable(List<Route> rsRoute) {
			ObservableList<Map<String, String>> dataRoute = FXCollections.observableArrayList();
			for (Route r : rsRoute) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getRouteId() != null)
					unit.put("RouteId", String.valueOf(r.getRouteId()));
				else
					unit.put("RouteId", "");
				if (r.getStartStation() != null)
					unit.put("StartStation", String.valueOf(r.getStartStation()));
				else
					unit.put("StartStation", "");
				if (r.getEndStation() != null)
					unit.put("EndStation", String.valueOf(r.getEndStation()));
				else
					unit.put("EndStation", "");
				if (r.getTime() != null)
					unit.put("Time", String.valueOf(r.getTime()));
				else
					unit.put("Time", "");
				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				dataRoute.add(unit);
			}
			
			allObjectTables.get("Route").setItems(dataRoute);
	}
	public void updateTrainTable(List<Train> rsTrain) {
			ObservableList<Map<String, String>> dataTrain = FXCollections.observableArrayList();
			for (Train r : rsTrain) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("TrainType", String.valueOf(r.getTrainType()));
				dataTrain.add(unit);
			}
			
			allObjectTables.get("Train").setItems(dataTrain);
	}
	public void updateTicketTable(List<Ticket> rsTicket) {
			ObservableList<Map<String, String>> dataTicket = FXCollections.observableArrayList();
			for (Ticket r : rsTicket) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getTicketId() != null)
					unit.put("TicketId", String.valueOf(r.getTicketId()));
				else
					unit.put("TicketId", "");
				if (r.getRouteId() != null)
					unit.put("RouteId", String.valueOf(r.getRouteId()));
				else
					unit.put("RouteId", "");
				if (r.getSeatId() != null)
					unit.put("SeatId", String.valueOf(r.getSeatId()));
				else
					unit.put("SeatId", "");
				unit.put("Price", String.valueOf(r.getPrice()));
				unit.put("IsValid", String.valueOf(r.getIsValid()));
				dataTicket.add(unit);
			}
			
			allObjectTables.get("Ticket").setItems(dataTicket);
	}
	public void updateOrderTable(List<Order> rsOrder) {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getOrderId() != null)
					unit.put("OrderId", String.valueOf(r.getOrderId()));
				else
					unit.put("OrderId", "");
				if (r.getTicketId() != null)
					unit.put("TicketId", String.valueOf(r.getTicketId()));
				else
					unit.put("TicketId", "");
				if (r.getAccoutId() != null)
					unit.put("AccoutId", String.valueOf(r.getAccoutId()));
				else
					unit.put("AccoutId", "");
				if (r.getCreateTime() != null)
					unit.put("CreateTime", String.valueOf(r.getCreateTime()));
				else
					unit.put("CreateTime", "");
				unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateAccoutTable(List<Accout> rsAccout) {
			ObservableList<Map<String, String>> dataAccout = FXCollections.observableArrayList();
			for (Accout r : rsAccout) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getAccoutId() != null)
					unit.put("AccoutId", String.valueOf(r.getAccoutId()));
				else
					unit.put("AccoutId", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPhoneNumber() != null)
					unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
				else
					unit.put("PhoneNumber", "");
				dataAccout.add(unit);
			}
			
			allObjectTables.get("Accout").setItems(dataAccout);
	}
	public void updateSeatTable(List<Seat> rsSeat) {
			ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
			for (Seat r : rsSeat) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getSeatId() != null)
					unit.put("SeatId", String.valueOf(r.getSeatId()));
				else
					unit.put("SeatId", "");
				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				unit.put("SeatType", String.valueOf(r.getSeatType()));
				dataSeat.add(unit);
			}
			
			allObjectTables.get("Seat").setItems(dataSeat);
	}
	
	/* 
	* update all object tables
	*/ 
	public void updateRouteTable() {
			ObservableList<Map<String, String>> dataRoute = FXCollections.observableArrayList();
			List<Route> rsRoute = EntityManager.getAllInstancesOf("Route");
			for (Route r : rsRoute) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getRouteId() != null)
					unit.put("RouteId", String.valueOf(r.getRouteId()));
				else
					unit.put("RouteId", "");
				if (r.getStartStation() != null)
					unit.put("StartStation", String.valueOf(r.getStartStation()));
				else
					unit.put("StartStation", "");
				if (r.getEndStation() != null)
					unit.put("EndStation", String.valueOf(r.getEndStation()));
				else
					unit.put("EndStation", "");
				if (r.getTime() != null)
					unit.put("Time", String.valueOf(r.getTime()));
				else
					unit.put("Time", "");
				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				dataRoute.add(unit);
			}
			
			allObjectTables.get("Route").setItems(dataRoute);
	}
	public void updateTrainTable() {
			ObservableList<Map<String, String>> dataTrain = FXCollections.observableArrayList();
			List<Train> rsTrain = EntityManager.getAllInstancesOf("Train");
			for (Train r : rsTrain) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("TrainType", String.valueOf(r.getTrainType()));
				dataTrain.add(unit);
			}
			
			allObjectTables.get("Train").setItems(dataTrain);
	}
	public void updateTicketTable() {
			ObservableList<Map<String, String>> dataTicket = FXCollections.observableArrayList();
			List<Ticket> rsTicket = EntityManager.getAllInstancesOf("Ticket");
			for (Ticket r : rsTicket) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getTicketId() != null)
					unit.put("TicketId", String.valueOf(r.getTicketId()));
				else
					unit.put("TicketId", "");
				if (r.getRouteId() != null)
					unit.put("RouteId", String.valueOf(r.getRouteId()));
				else
					unit.put("RouteId", "");
				if (r.getSeatId() != null)
					unit.put("SeatId", String.valueOf(r.getSeatId()));
				else
					unit.put("SeatId", "");
				unit.put("Price", String.valueOf(r.getPrice()));
				unit.put("IsValid", String.valueOf(r.getIsValid()));
				dataTicket.add(unit);
			}
			
			allObjectTables.get("Ticket").setItems(dataTicket);
	}
	public void updateOrderTable() {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getOrderId() != null)
					unit.put("OrderId", String.valueOf(r.getOrderId()));
				else
					unit.put("OrderId", "");
				if (r.getTicketId() != null)
					unit.put("TicketId", String.valueOf(r.getTicketId()));
				else
					unit.put("TicketId", "");
				if (r.getAccoutId() != null)
					unit.put("AccoutId", String.valueOf(r.getAccoutId()));
				else
					unit.put("AccoutId", "");
				if (r.getCreateTime() != null)
					unit.put("CreateTime", String.valueOf(r.getCreateTime()));
				else
					unit.put("CreateTime", "");
				unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateAccoutTable() {
			ObservableList<Map<String, String>> dataAccout = FXCollections.observableArrayList();
			List<Accout> rsAccout = EntityManager.getAllInstancesOf("Accout");
			for (Accout r : rsAccout) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getAccoutId() != null)
					unit.put("AccoutId", String.valueOf(r.getAccoutId()));
				else
					unit.put("AccoutId", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPhoneNumber() != null)
					unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
				else
					unit.put("PhoneNumber", "");
				dataAccout.add(unit);
			}
			
			allObjectTables.get("Accout").setItems(dataAccout);
	}
	public void updateSeatTable() {
			ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
			List<Seat> rsSeat = EntityManager.getAllInstancesOf("Seat");
			for (Seat r : rsSeat) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getSeatId() != null)
					unit.put("SeatId", String.valueOf(r.getSeatId()));
				else
					unit.put("SeatId", "");
				if (r.getTrainId() != null)
					unit.put("TrainId", String.valueOf(r.getTrainId()));
				else
					unit.put("TrainId", "");
				unit.put("SeatType", String.valueOf(r.getSeatType()));
				dataSeat.add(unit);
			}
			
			allObjectTables.get("Seat").setItems(dataSeat);
	}
	
	public void classStatisicBingding() {
	
		 classInfodata = FXCollections.observableArrayList();
	 	 route = new ClassInfo("Route", EntityManager.getAllInstancesOf("Route").size());
	 	 classInfodata.add(route);
	 	 train = new ClassInfo("Train", EntityManager.getAllInstancesOf("Train").size());
	 	 classInfodata.add(train);
	 	 ticket = new ClassInfo("Ticket", EntityManager.getAllInstancesOf("Ticket").size());
	 	 classInfodata.add(ticket);
	 	 order = new ClassInfo("Order", EntityManager.getAllInstancesOf("Order").size());
	 	 classInfodata.add(order);
	 	 accout = new ClassInfo("Accout", EntityManager.getAllInstancesOf("Accout").size());
	 	 classInfodata.add(accout);
	 	 seat = new ClassInfo("Seat", EntityManager.getAllInstancesOf("Seat").size());
	 	 classInfodata.add(seat);
	 	 
		 class_statisic.setItems(classInfodata);
		 
		 //Class Statisic Binding
		 class_statisic.getSelectionModel().selectedItemProperty().addListener(
				 (observable, oldValue, newValue) ->  { 
				 										 //no selected object in table
				 										 objectindex = -1;
				 										 
				 										 //get lastest data, reflect updateTableData method
				 										 try {
												 			 Method updateob = this.getClass().getMethod("update" + newValue.getName() + "Table", null);
												 			 updateob.invoke(this);			 
												 		 } catch (Exception e) {
												 		 	 e.printStackTrace();
												 		 }		 										 
				 	
				 										 //show object table
				 			 				 			 TableView obs = allObjectTables.get(newValue.getName());
				 			 				 			 if (obs != null) {
				 			 				 				object_statics.setContent(obs);
				 			 				 				object_statics.setText("All Objects " + newValue.getName() + ":");
				 			 				 			 }
				 			 				 			 
				 			 				 			 //update association information
							 			 				 updateAssociation(newValue.getName());
				 			 				 			 
				 			 				 			 //show association information
				 			 				 			 ObservableList<AssociationInfo> asso = allassociationData.get(newValue.getName());
				 			 				 			 if (asso != null) {
				 			 				 			 	association_statisic.setItems(asso);
				 			 				 			 }
				 			 				 		  });
	}
	
	public void classStatisticUpdate() {
	 	 route.setNumber(EntityManager.getAllInstancesOf("Route").size());
	 	 train.setNumber(EntityManager.getAllInstancesOf("Train").size());
	 	 ticket.setNumber(EntityManager.getAllInstancesOf("Ticket").size());
	 	 order.setNumber(EntityManager.getAllInstancesOf("Order").size());
	 	 accout.setNumber(EntityManager.getAllInstancesOf("Accout").size());
	 	 seat.setNumber(EntityManager.getAllInstancesOf("Seat").size());
		
	}
	
	/**
	 * association binding
	 */
	public void associationStatisicBingding() {
		
		allassociationData = new HashMap<String, ObservableList<AssociationInfo>>();
		
		ObservableList<AssociationInfo> Route_association_data = FXCollections.observableArrayList();
		AssociationInfo Route_associatition_InTicket = new AssociationInfo("Route", "Ticket", "InTicket", true);
		Route_association_data.add(Route_associatition_InTicket);
		AssociationInfo Route_associatition_UsedTrain = new AssociationInfo("Route", "Train", "UsedTrain", false);
		Route_association_data.add(Route_associatition_UsedTrain);
		
		allassociationData.put("Route", Route_association_data);
		
		ObservableList<AssociationInfo> Train_association_data = FXCollections.observableArrayList();
		AssociationInfo Train_associatition_InRoute = new AssociationInfo("Train", "Route", "InRoute", true);
		Train_association_data.add(Train_associatition_InRoute);
		
		allassociationData.put("Train", Train_association_data);
		
		ObservableList<AssociationInfo> Ticket_association_data = FXCollections.observableArrayList();
		AssociationInfo Ticket_associatition_HasRoute = new AssociationInfo("Ticket", "Route", "HasRoute", false);
		Ticket_association_data.add(Ticket_associatition_HasRoute);
		AssociationInfo Ticket_associatition_InOrder = new AssociationInfo("Ticket", "Order", "InOrder", false);
		Ticket_association_data.add(Ticket_associatition_InOrder);
		AssociationInfo Ticket_associatition_HasSeat = new AssociationInfo("Ticket", "Seat", "HasSeat", false);
		Ticket_association_data.add(Ticket_associatition_HasSeat);
		AssociationInfo Ticket_associatition_BelongedAccout = new AssociationInfo("Ticket", "Accout", "BelongedAccout", false);
		Ticket_association_data.add(Ticket_associatition_BelongedAccout);
		
		allassociationData.put("Ticket", Ticket_association_data);
		
		ObservableList<AssociationInfo> Order_association_data = FXCollections.observableArrayList();
		AssociationInfo Order_associatition_HasTicket = new AssociationInfo("Order", "Ticket", "HasTicket", false);
		Order_association_data.add(Order_associatition_HasTicket);
		AssociationInfo Order_associatition_HasAccout = new AssociationInfo("Order", "Accout", "HasAccout", false);
		Order_association_data.add(Order_associatition_HasAccout);
		
		allassociationData.put("Order", Order_association_data);
		
		ObservableList<AssociationInfo> Accout_association_data = FXCollections.observableArrayList();
		AssociationInfo Accout_associatition_AccouttoSeat = new AssociationInfo("Accout", "Seat", "AccouttoSeat", false);
		Accout_association_data.add(Accout_associatition_AccouttoSeat);
		AssociationInfo Accout_associatition_InOrder = new AssociationInfo("Accout", "Order", "InOrder", false);
		Accout_association_data.add(Accout_associatition_InOrder);
		AssociationInfo Accout_associatition_HasTicket = new AssociationInfo("Accout", "Ticket", "HasTicket", true);
		Accout_association_data.add(Accout_associatition_HasTicket);
		
		allassociationData.put("Accout", Accout_association_data);
		
		ObservableList<AssociationInfo> Seat_association_data = FXCollections.observableArrayList();
		AssociationInfo Seat_associatition_InTicket = new AssociationInfo("Seat", "Ticket", "InTicket", true);
		Seat_association_data.add(Seat_associatition_InTicket);
		
		allassociationData.put("Seat", Seat_association_data);
		
		
		association_statisic.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) ->  { 
	
							 		if (newValue != null) {
							 			 try {
							 			 	 if (newValue.getNumber() != 0) {
								 				 //choose object or not
								 				 if (objectindex != -1) {
									 				 Class[] cArg = new Class[1];
									 				 cArg[0] = List.class;
									 				 //reflect updateTableData method
										 			 Method updateob = this.getClass().getMethod("update" + newValue.getTargetClass() + "Table", cArg);
										 			 //find choosen object
										 			 Object selectedob = EntityManager.getAllInstancesOf(newValue.getSourceClass()).get(objectindex);
										 			 //reflect find association method
										 			 Method getAssociatedObject = selectedob.getClass().getMethod("get" + newValue.getAssociationName());
										 			 List r = new LinkedList();
										 			 //one or mulity?
										 			 if (newValue.getIsMultiple() == true) {
											 			 
											 			r = (List) getAssociatedObject.invoke(selectedob);
										 			 }
										 			 else {
										 				r.add(getAssociatedObject.invoke(selectedob));
										 			 }
										 			 //invoke update method
										 			 updateob.invoke(this, r);
										 			  
										 			 
								 				 }
												 //bind updated data to GUI
					 				 			 TableView obs = allObjectTables.get(newValue.getTargetClass());
					 				 			 if (obs != null) {
					 				 				object_statics.setContent(obs);
					 				 				object_statics.setText("Targets Objects " + newValue.getTargetClass() + ":");
					 				 			 }
					 				 		 }
							 			 }
							 			 catch (Exception e) {
							 				 e.printStackTrace();
							 			 }
							 		}
					 		  });
		
	}	
	
	

    //prepare data for contract
	public void prepareData() {
		
		//definition map
		definitions_map = new HashMap<String, String>();
		definitions_map.put("createRoute", "route:Route = Route.allInstance()->any(rou:Route | rou.RouteId = routeid)\n\rtrain:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("queryRoute", "route:Route = Route.allInstance()->any(rou:Route | rou.RouteId = routeid)\r\r\n");
		definitions_map.put("modifyRoute", "route:Route = Route.allInstance()->any(rou:Route | rou.RouteId = routeid)\n\rtrain:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("deleteRoute", "route:Route = Route.allInstance()->any(rou:Route | rou.RouteId = routeid)\r\r\n");
		definitions_map.put("createTrain", "train:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("queryTrain", "train:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("modifyTrain", "train:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("deleteTrain", "train:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("createTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketid)\r\r\n");
		definitions_map.put("queryTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketid)\r\r\n");
		definitions_map.put("modifyTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketid)\r\r\n");
		definitions_map.put("deleteTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketid)\r\r\n");
		definitions_map.put("createOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.OrderId = orderid)\r\r\n");
		definitions_map.put("queryOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.OrderId = orderid)\r\r\n");
		definitions_map.put("modifyOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.OrderId = orderid)\r\r\n");
		definitions_map.put("deleteOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.OrderId = orderid)\r\r\n");
		definitions_map.put("createAccout", "accout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutid)\r\r\n");
		definitions_map.put("queryAccout", "accout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutid)\r\r\n");
		definitions_map.put("modifyAccout", "accout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutid)\r\r\n");
		definitions_map.put("deleteAccout", "accout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutid)\r\r\n");
		definitions_map.put("createSeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.SeatId = seatid)\n\rtrain:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("querySeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.SeatId = seatid)\r\r\n");
		definitions_map.put("modifySeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.SeatId = seatid)\n\rtrain:Train = Train.allInstance()->any(tra:Train | tra.TrainId = trainid)\r\r\n");
		definitions_map.put("deleteSeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.SeatId = seatid)\r\r\n");
		definitions_map.put("buyTicket", "route:Route = Route.allInstance()->any(rou:Route | rou.RouteId = routeId)\n\raccout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutId)\n\rticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketId)\r\r\n");
		definitions_map.put("saveOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.OrderId = orderId)\r\r\n");
		definitions_map.put("selectSeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.TrainId = CurrentRoute.TrainId and sea.SeatId = seatId)\r\r\n");
		definitions_map.put("cancelTrip", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketId)\n\raccout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutId)\n\rorder:Order = Order.allInstance()->any(ord:Order | ord.TicketId = ticketId and ord.AccoutId = accoutId and ord.OrderStatus = OrderStatus :: PAID)\r\r\n");
		definitions_map.put("queryByAccoutId", "accout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutId)\r\r\n");
		definitions_map.put("queryTicketId", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketId)\r\r\n");
		definitions_map.put("updateTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketId and tic.IsValid = true)\n\raccout:Accout = Accout.allInstance()->any(acc:Accout | acc.AccoutId = accoutId)\n\rroute:Route = Route.allInstance()->any(rou:Route | rou.RouteId = newRouteId)\n\rorder:Order = Order.allInstance()->any(ord:Order | ord.AccoutId = accoutId and ord.TicketId = ticketId and ord.OrderStatus = OrderStatus :: PAID)\r\r\n");
		definitions_map.put("selectNewSeat", "seat:Seat = Seat.allInstance()->any(sea:Seat | sea.SeatId = seatId and sea.TrainId = CurrentRoute.TrainId)\r\r\n");
		definitions_map.put("deleteOverdueTicket", "ticket:Ticket = Ticket.allInstance()->any(tic:Ticket | tic.TicketId = ticketId and tic.IsValid = false)\r\r\n");
		definitions_map.put("deleteOverdueOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.TicketId = CurrentTicketId and ord.OrderStatus = OrderStatus :: CANCELED)\r\r\n");
		
		//precondition map
		preconditions_map = new HashMap<String, String>();
		preconditions_map.put("createRoute", "route.oclIsUndefined() = true and\ntrain.oclIsUndefined() = false\n");
		preconditions_map.put("queryRoute", "route.oclIsUndefined() = false");
		preconditions_map.put("modifyRoute", "route.oclIsUndefined() = false and\ntrain.oclIsUndefined() = false\n");
		preconditions_map.put("deleteRoute", "route.oclIsUndefined() = false and\nRoute.allInstance()->includes(route)\n");
		preconditions_map.put("createTrain", "train.oclIsUndefined() = true");
		preconditions_map.put("queryTrain", "train.oclIsUndefined() = false");
		preconditions_map.put("modifyTrain", "train.oclIsUndefined() = false");
		preconditions_map.put("deleteTrain", "train.oclIsUndefined() = false and\nTrain.allInstance()->includes(train)\n");
		preconditions_map.put("createTicket", "ticket.oclIsUndefined() = true");
		preconditions_map.put("queryTicket", "ticket.oclIsUndefined() = false");
		preconditions_map.put("modifyTicket", "ticket.oclIsUndefined() = false");
		preconditions_map.put("deleteTicket", "ticket.oclIsUndefined() = false and\nTicket.allInstance()->includes(ticket)\n");
		preconditions_map.put("createOrder", "order.oclIsUndefined() = true");
		preconditions_map.put("queryOrder", "order.oclIsUndefined() = false");
		preconditions_map.put("modifyOrder", "order.oclIsUndefined() = false");
		preconditions_map.put("deleteOrder", "order.oclIsUndefined() = false and\nOrder.allInstance()->includes(order)\n");
		preconditions_map.put("createAccout", "accout.oclIsUndefined() = true");
		preconditions_map.put("queryAccout", "accout.oclIsUndefined() = false");
		preconditions_map.put("modifyAccout", "accout.oclIsUndefined() = false");
		preconditions_map.put("deleteAccout", "accout.oclIsUndefined() = false and\nAccout.allInstance()->includes(accout)\n");
		preconditions_map.put("createSeat", "seat.oclIsUndefined() = true and\ntrain.oclIsUndefined() = false\n");
		preconditions_map.put("querySeat", "seat.oclIsUndefined() = false");
		preconditions_map.put("modifySeat", "seat.oclIsUndefined() = false and\ntrain.oclIsUndefined() = false\n");
		preconditions_map.put("deleteSeat", "seat.oclIsUndefined() = false and\nSeat.allInstance()->includes(seat)\n");
		preconditions_map.put("buyTicket", "route.oclIsUndefined() = false and\naccout.oclIsUndefined() = false and\nticket.oclIsUndefined() = true\n");
		preconditions_map.put("saveOrder", "order.oclIsUndefined() = true");
		preconditions_map.put("showSeats", "true");
		preconditions_map.put("selectSeat", "seat.oclIsUndefined() = false");
		preconditions_map.put("pay", "price > 0");
		preconditions_map.put("queryInformation", "true");
		preconditions_map.put("cancelTrip", "//true\nticket.oclIsUndefined() = false and\naccout.oclIsUndefined() = false and\norder.oclIsUndefined() = false\n");
		preconditions_map.put("queryByAccoutId", "accout.oclIsUndefined() = false");
		preconditions_map.put("queryTicketId", "ticket.oclIsUndefined() = false");
		preconditions_map.put("updateTicket", "ticket.oclIsUndefined() = false and\naccout.oclIsUndefined() = false and\nroute.oclIsUndefined() = false and\norder.oclIsUndefined() = false\n");
		preconditions_map.put("updateOrder", "true");
		preconditions_map.put("showSeatsByRouteId", "true");
		preconditions_map.put("selectNewSeat", "seat.oclIsUndefined() = false");
		preconditions_map.put("payDifference", "true");
		preconditions_map.put("deleteOverdueTicket", "ticket.oclIsUndefined() = false");
		preconditions_map.put("deleteOverdueOrder", "order.oclIsUndefined() = false");
		preconditions_map.put("sendNotification", "true");
		
		//postcondition map
		postconditions_map = new HashMap<String, String>();
		postconditions_map.put("createRoute", "let rou:Route inrou.oclIsNew() and\nrou.RouteId = routeid and\nrou.StartStation = startstation and\nrou.EndStation = endstation and\nrou.Time = time and\nrou.TrainId = trainid and\nRoute.allInstance()->includes(rou) and\nresult = true\n");
		postconditions_map.put("queryRoute", "result = route");
		postconditions_map.put("modifyRoute", "route.RouteId = routeid and\nroute.StartStation = startstation and\nroute.EndStation = endstation and\nroute.Time = time and\nroute.TrainId = trainid and\nresult = true\n");
		postconditions_map.put("deleteRoute", "Route.allInstance()->excludes(route) and\nresult = true\n");
		postconditions_map.put("createTrain", "let tra:Train intra.oclIsNew() and\ntra.TrainId = trainid and\ntra.Name = name and\ntra.TrainType = traintype and\nTrain.allInstance()->includes(tra) and\nresult = true\n");
		postconditions_map.put("queryTrain", "result = train");
		postconditions_map.put("modifyTrain", "train.TrainId = trainid and\ntrain.Name = name and\ntrain.TrainType = traintype and\nresult = true\n");
		postconditions_map.put("deleteTrain", "Train.allInstance()->excludes(train) and\nresult = true\n");
		postconditions_map.put("createTicket", "let tic:Ticket intic.oclIsNew() and\ntic.TicketId = ticketid and\ntic.RouteId = routeid and\ntic.SeatId = seatid and\ntic.Price = price and\ntic.IsValid = isvalid and\nTicket.allInstance()->includes(tic) and\nresult = true\n");
		postconditions_map.put("queryTicket", "result = ticket");
		postconditions_map.put("modifyTicket", "ticket.TicketId = ticketid and\nticket.RouteId = routeid and\nticket.SeatId = seatid and\nticket.Price = price and\nticket.IsValid = isvalid and\nresult = true\n");
		postconditions_map.put("deleteTicket", "Ticket.allInstance()->excludes(ticket) and\nresult = true\n");
		postconditions_map.put("createOrder", "let ord:Order inord.oclIsNew() and\nord.OrderId = orderid and\nord.TicketId = ticketid and\nord.AccoutId = accoutid and\nord.CreateTime = createtime and\nord.OrderStatus = orderstatus and\nOrder.allInstance()->includes(ord) and\nresult = true\n");
		postconditions_map.put("queryOrder", "result = order");
		postconditions_map.put("modifyOrder", "order.OrderId = orderid and\norder.TicketId = ticketid and\norder.AccoutId = accoutid and\norder.CreateTime = createtime and\norder.OrderStatus = orderstatus and\nresult = true\n");
		postconditions_map.put("deleteOrder", "Order.allInstance()->excludes(order) and\nresult = true\n");
		postconditions_map.put("createAccout", "let acc:Accout inacc.oclIsNew() and\nacc.AccoutId = accoutid and\nacc.Name = name and\nacc.PhoneNumber = phonenumber and\nAccout.allInstance()->includes(acc) and\nresult = true\n");
		postconditions_map.put("queryAccout", "result = accout");
		postconditions_map.put("modifyAccout", "accout.AccoutId = accoutid and\naccout.Name = name and\naccout.PhoneNumber = phonenumber and\nresult = true\n");
		postconditions_map.put("deleteAccout", "Accout.allInstance()->excludes(accout) and\nresult = true\n");
		postconditions_map.put("createSeat", "let sea:Seat insea.oclIsNew() and\nsea.SeatId = seatid and\nsea.TrainId = trainid and\nsea.SeatType = seattype and\nSeat.allInstance()->includes(sea) and\nresult = true\n");
		postconditions_map.put("querySeat", "result = seat");
		postconditions_map.put("modifySeat", "seat.SeatId = seatid and\nseat.TrainId = trainid and\nseat.SeatType = seattype and\nresult = true\n");
		postconditions_map.put("deleteSeat", "Seat.allInstance()->excludes(seat) and\nresult = true\n");
		postconditions_map.put("buyTicket", "let tic:Ticket intic.oclIsNew() and\ntic.TicketId = ticketId and\ntic.RouteId = routeId and\ntic.IsValid = false and\nTicket.allInstance()->includes(tic) and\nCurrentTicket = tic and\nCurrentAccout = accout and\nCurrentRoute = route and\nresult = true\n");
		postconditions_map.put("saveOrder", "let ord:Order inord.oclIsNew() and\nord.OrderId = orderId and\nord.TicketId = CurrentTicket.TicketId and\nord.AccoutId = CurrentAccout.AccoutId and\nord.CreateTime = boughtTime and\nord.OrderStatus = OrderStatus::NOTPAID and\nCurrentOrder = ord and\nOrder.allInstance()->includes(ord) and\nresult = true\n");
		postconditions_map.put("showSeats", "result = Seat.allInstance()->select(sea:Seat | sea.TrainId =  CurrentRoute.TrainId)");
		postconditions_map.put("selectSeat", "CurrentTicket.seatId = seatId and\nresult = true\n");
		postconditions_map.put("pay", "CurrentTicket.Price = price and\nCurrentTicket.IsValid = true and\nCurrentOrder.OrderStatus = OrderStatus::PAID and\nsendNotification(CurrentAccout.PhoneNumber) and\nresult = true\n");
		postconditions_map.put("queryInformation", "result = Route.allInstance()->select(route:Route | route.StartStation = startStation and route.EndStation = endStation and route.Time = time)");
		postconditions_map.put("cancelTrip", "ticket.IsValid = false and\norder.OrderStatus = OrderStatus::CANCELED and\nresult = true\n");
		postconditions_map.put("queryByAccoutId", "result = Order.allInstance()->select(order:Order | order.AccoutId = accoutId)");
		postconditions_map.put("queryTicketId", "result = Order.allInstance()->select(order:Order | order.TicketId = ticketId)");
		postconditions_map.put("updateTicket", "ticket.RouteId = newRouteId and\nticket.IsValid = false and\nCurrentTicket = ticket and\nCurrentOrder = order and\nCurrentAccout = accout and\nCurrentRoute = route and\nresult = true\n");
		postconditions_map.put("updateOrder", "CurrentOrder.CreateTime = time and\nCurrentOrder.OrderStatus = OrderStatus::NOTPAID and\nresult = true\n");
		postconditions_map.put("showSeatsByRouteId", "result = Seat.allInstance()->select(sea:Seat | sea.TrainId =  CurrentRoute.TrainId)");
		postconditions_map.put("selectNewSeat", "self.CurrentTicket.SeatId = seatId and\nresult = true\n");
		postconditions_map.put("payDifference", "CurrentTicket.IsValid = true and\nCurrentTicket.Price = CurrentTicket.Price + difference and\nCurrentOrder.OrderStatus = OrderStatus::PAID and\nsendNotification(CurrentAccout.PhoneNumber) and\nresult = true\n");
		postconditions_map.put("deleteOverdueTicket", "self.CurrentTicketId = ticketId and\nTicket.allInstance()->excludes(ticket) and\nresult = true\n");
		postconditions_map.put("deleteOverdueOrder", "Order.allInstance()->excludes(order) and\nresult = true\n");
		postconditions_map.put("sendNotification", "result = true");
		
		//service invariants map
		service_invariants_map = new LinkedHashMap<String, String>();
		
		//entity invariants map
		entity_invariants_map = new LinkedHashMap<String, String>();
		
	}
	
	public void generatOperationPane() {
		
		 operationPanels = new LinkedHashMap<String, GridPane>();
		
		 // ==================== GridPane_createRoute ====================
		 GridPane createRoute = new GridPane();
		 createRoute.setHgap(4);
		 createRoute.setVgap(6);
		 createRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createRoute_content = createRoute.getChildren();
		 Label createRoute_routeid_label = new Label("routeid:");
		 createRoute_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRoute_content.add(createRoute_routeid_label);
		 GridPane.setConstraints(createRoute_routeid_label, 0, 0);
		 
		 createRoute_routeid_t = new TextField();
		 createRoute_content.add(createRoute_routeid_t);
		 createRoute_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRoute_routeid_t, 1, 0);
		 Label createRoute_startstation_label = new Label("startstation:");
		 createRoute_startstation_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRoute_content.add(createRoute_startstation_label);
		 GridPane.setConstraints(createRoute_startstation_label, 0, 1);
		 
		 createRoute_startstation_t = new TextField();
		 createRoute_content.add(createRoute_startstation_t);
		 createRoute_startstation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRoute_startstation_t, 1, 1);
		 Label createRoute_endstation_label = new Label("endstation:");
		 createRoute_endstation_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRoute_content.add(createRoute_endstation_label);
		 GridPane.setConstraints(createRoute_endstation_label, 0, 2);
		 
		 createRoute_endstation_t = new TextField();
		 createRoute_content.add(createRoute_endstation_t);
		 createRoute_endstation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRoute_endstation_t, 1, 2);
		 Label createRoute_time_label = new Label("time:");
		 createRoute_time_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRoute_content.add(createRoute_time_label);
		 GridPane.setConstraints(createRoute_time_label, 0, 3);
		 
		 createRoute_time_t = new TextField();
		 createRoute_content.add(createRoute_time_t);
		 createRoute_time_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRoute_time_t, 1, 3);
		 Label createRoute_trainid_label = new Label("trainid:");
		 createRoute_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createRoute_content.add(createRoute_trainid_label);
		 GridPane.setConstraints(createRoute_trainid_label, 0, 4);
		 
		 createRoute_trainid_t = new TextField();
		 createRoute_content.add(createRoute_trainid_t);
		 createRoute_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createRoute_trainid_t, 1, 4);
		 operationPanels.put("createRoute", createRoute);
		 
		 // ==================== GridPane_queryRoute ====================
		 GridPane queryRoute = new GridPane();
		 queryRoute.setHgap(4);
		 queryRoute.setVgap(6);
		 queryRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryRoute_content = queryRoute.getChildren();
		 Label queryRoute_routeid_label = new Label("routeid:");
		 queryRoute_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryRoute_content.add(queryRoute_routeid_label);
		 GridPane.setConstraints(queryRoute_routeid_label, 0, 0);
		 
		 queryRoute_routeid_t = new TextField();
		 queryRoute_content.add(queryRoute_routeid_t);
		 queryRoute_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryRoute_routeid_t, 1, 0);
		 operationPanels.put("queryRoute", queryRoute);
		 
		 // ==================== GridPane_modifyRoute ====================
		 GridPane modifyRoute = new GridPane();
		 modifyRoute.setHgap(4);
		 modifyRoute.setVgap(6);
		 modifyRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyRoute_content = modifyRoute.getChildren();
		 Label modifyRoute_routeid_label = new Label("routeid:");
		 modifyRoute_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyRoute_content.add(modifyRoute_routeid_label);
		 GridPane.setConstraints(modifyRoute_routeid_label, 0, 0);
		 
		 modifyRoute_routeid_t = new TextField();
		 modifyRoute_content.add(modifyRoute_routeid_t);
		 modifyRoute_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyRoute_routeid_t, 1, 0);
		 Label modifyRoute_startstation_label = new Label("startstation:");
		 modifyRoute_startstation_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyRoute_content.add(modifyRoute_startstation_label);
		 GridPane.setConstraints(modifyRoute_startstation_label, 0, 1);
		 
		 modifyRoute_startstation_t = new TextField();
		 modifyRoute_content.add(modifyRoute_startstation_t);
		 modifyRoute_startstation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyRoute_startstation_t, 1, 1);
		 Label modifyRoute_endstation_label = new Label("endstation:");
		 modifyRoute_endstation_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyRoute_content.add(modifyRoute_endstation_label);
		 GridPane.setConstraints(modifyRoute_endstation_label, 0, 2);
		 
		 modifyRoute_endstation_t = new TextField();
		 modifyRoute_content.add(modifyRoute_endstation_t);
		 modifyRoute_endstation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyRoute_endstation_t, 1, 2);
		 Label modifyRoute_time_label = new Label("time:");
		 modifyRoute_time_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyRoute_content.add(modifyRoute_time_label);
		 GridPane.setConstraints(modifyRoute_time_label, 0, 3);
		 
		 modifyRoute_time_t = new TextField();
		 modifyRoute_content.add(modifyRoute_time_t);
		 modifyRoute_time_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyRoute_time_t, 1, 3);
		 Label modifyRoute_trainid_label = new Label("trainid:");
		 modifyRoute_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyRoute_content.add(modifyRoute_trainid_label);
		 GridPane.setConstraints(modifyRoute_trainid_label, 0, 4);
		 
		 modifyRoute_trainid_t = new TextField();
		 modifyRoute_content.add(modifyRoute_trainid_t);
		 modifyRoute_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyRoute_trainid_t, 1, 4);
		 operationPanels.put("modifyRoute", modifyRoute);
		 
		 // ==================== GridPane_deleteRoute ====================
		 GridPane deleteRoute = new GridPane();
		 deleteRoute.setHgap(4);
		 deleteRoute.setVgap(6);
		 deleteRoute.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteRoute_content = deleteRoute.getChildren();
		 Label deleteRoute_routeid_label = new Label("routeid:");
		 deleteRoute_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteRoute_content.add(deleteRoute_routeid_label);
		 GridPane.setConstraints(deleteRoute_routeid_label, 0, 0);
		 
		 deleteRoute_routeid_t = new TextField();
		 deleteRoute_content.add(deleteRoute_routeid_t);
		 deleteRoute_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteRoute_routeid_t, 1, 0);
		 operationPanels.put("deleteRoute", deleteRoute);
		 
		 // ==================== GridPane_createTrain ====================
		 GridPane createTrain = new GridPane();
		 createTrain.setHgap(4);
		 createTrain.setVgap(6);
		 createTrain.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createTrain_content = createTrain.getChildren();
		 Label createTrain_trainid_label = new Label("trainid:");
		 createTrain_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTrain_content.add(createTrain_trainid_label);
		 GridPane.setConstraints(createTrain_trainid_label, 0, 0);
		 
		 createTrain_trainid_t = new TextField();
		 createTrain_content.add(createTrain_trainid_t);
		 createTrain_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTrain_trainid_t, 1, 0);
		 Label createTrain_name_label = new Label("name:");
		 createTrain_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTrain_content.add(createTrain_name_label);
		 GridPane.setConstraints(createTrain_name_label, 0, 1);
		 
		 createTrain_name_t = new TextField();
		 createTrain_content.add(createTrain_name_t);
		 createTrain_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTrain_name_t, 1, 1);
		 Label createTrain_traintype_label = new Label("traintype:");
		 createTrain_traintype_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTrain_content.add(createTrain_traintype_label);
		 GridPane.setConstraints(createTrain_traintype_label, 0, 2);
		 
		 createTrain_traintype_cb = new ChoiceBox();
createTrain_traintype_cb.getItems().add("G");
createTrain_traintype_cb.getItems().add("D");
createTrain_traintype_cb.getItems().add("Z");
createTrain_traintype_cb.getItems().add("T");
createTrain_traintype_cb.getItems().add("A");
createTrain_traintype_cb.getItems().add("L");
		 createTrain_traintype_cb.getSelectionModel().selectFirst();
		 createTrain_content.add(createTrain_traintype_cb);
		 GridPane.setConstraints(createTrain_traintype_cb, 1, 2);
		 operationPanels.put("createTrain", createTrain);
		 
		 // ==================== GridPane_queryTrain ====================
		 GridPane queryTrain = new GridPane();
		 queryTrain.setHgap(4);
		 queryTrain.setVgap(6);
		 queryTrain.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryTrain_content = queryTrain.getChildren();
		 Label queryTrain_trainid_label = new Label("trainid:");
		 queryTrain_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryTrain_content.add(queryTrain_trainid_label);
		 GridPane.setConstraints(queryTrain_trainid_label, 0, 0);
		 
		 queryTrain_trainid_t = new TextField();
		 queryTrain_content.add(queryTrain_trainid_t);
		 queryTrain_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryTrain_trainid_t, 1, 0);
		 operationPanels.put("queryTrain", queryTrain);
		 
		 // ==================== GridPane_modifyTrain ====================
		 GridPane modifyTrain = new GridPane();
		 modifyTrain.setHgap(4);
		 modifyTrain.setVgap(6);
		 modifyTrain.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyTrain_content = modifyTrain.getChildren();
		 Label modifyTrain_trainid_label = new Label("trainid:");
		 modifyTrain_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTrain_content.add(modifyTrain_trainid_label);
		 GridPane.setConstraints(modifyTrain_trainid_label, 0, 0);
		 
		 modifyTrain_trainid_t = new TextField();
		 modifyTrain_content.add(modifyTrain_trainid_t);
		 modifyTrain_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTrain_trainid_t, 1, 0);
		 Label modifyTrain_name_label = new Label("name:");
		 modifyTrain_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTrain_content.add(modifyTrain_name_label);
		 GridPane.setConstraints(modifyTrain_name_label, 0, 1);
		 
		 modifyTrain_name_t = new TextField();
		 modifyTrain_content.add(modifyTrain_name_t);
		 modifyTrain_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTrain_name_t, 1, 1);
		 Label modifyTrain_traintype_label = new Label("traintype:");
		 modifyTrain_traintype_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTrain_content.add(modifyTrain_traintype_label);
		 GridPane.setConstraints(modifyTrain_traintype_label, 0, 2);
		 
		 modifyTrain_traintype_cb = new ChoiceBox();
modifyTrain_traintype_cb.getItems().add("G");
modifyTrain_traintype_cb.getItems().add("D");
modifyTrain_traintype_cb.getItems().add("Z");
modifyTrain_traintype_cb.getItems().add("T");
modifyTrain_traintype_cb.getItems().add("A");
modifyTrain_traintype_cb.getItems().add("L");
		 modifyTrain_traintype_cb.getSelectionModel().selectFirst();
		 modifyTrain_content.add(modifyTrain_traintype_cb);
		 GridPane.setConstraints(modifyTrain_traintype_cb, 1, 2);
		 operationPanels.put("modifyTrain", modifyTrain);
		 
		 // ==================== GridPane_deleteTrain ====================
		 GridPane deleteTrain = new GridPane();
		 deleteTrain.setHgap(4);
		 deleteTrain.setVgap(6);
		 deleteTrain.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteTrain_content = deleteTrain.getChildren();
		 Label deleteTrain_trainid_label = new Label("trainid:");
		 deleteTrain_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteTrain_content.add(deleteTrain_trainid_label);
		 GridPane.setConstraints(deleteTrain_trainid_label, 0, 0);
		 
		 deleteTrain_trainid_t = new TextField();
		 deleteTrain_content.add(deleteTrain_trainid_t);
		 deleteTrain_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteTrain_trainid_t, 1, 0);
		 operationPanels.put("deleteTrain", deleteTrain);
		 
		 // ==================== GridPane_createTicket ====================
		 GridPane createTicket = new GridPane();
		 createTicket.setHgap(4);
		 createTicket.setVgap(6);
		 createTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createTicket_content = createTicket.getChildren();
		 Label createTicket_ticketid_label = new Label("ticketid:");
		 createTicket_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTicket_content.add(createTicket_ticketid_label);
		 GridPane.setConstraints(createTicket_ticketid_label, 0, 0);
		 
		 createTicket_ticketid_t = new TextField();
		 createTicket_content.add(createTicket_ticketid_t);
		 createTicket_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTicket_ticketid_t, 1, 0);
		 Label createTicket_routeid_label = new Label("routeid:");
		 createTicket_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTicket_content.add(createTicket_routeid_label);
		 GridPane.setConstraints(createTicket_routeid_label, 0, 1);
		 
		 createTicket_routeid_t = new TextField();
		 createTicket_content.add(createTicket_routeid_t);
		 createTicket_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTicket_routeid_t, 1, 1);
		 Label createTicket_seatid_label = new Label("seatid:");
		 createTicket_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTicket_content.add(createTicket_seatid_label);
		 GridPane.setConstraints(createTicket_seatid_label, 0, 2);
		 
		 createTicket_seatid_t = new TextField();
		 createTicket_content.add(createTicket_seatid_t);
		 createTicket_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTicket_seatid_t, 1, 2);
		 Label createTicket_price_label = new Label("price:");
		 createTicket_price_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTicket_content.add(createTicket_price_label);
		 GridPane.setConstraints(createTicket_price_label, 0, 3);
		 
		 createTicket_price_t = new TextField();
		 createTicket_content.add(createTicket_price_t);
		 createTicket_price_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTicket_price_t, 1, 3);
		 Label createTicket_isvalid_label = new Label("isvalid:");
		 createTicket_isvalid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createTicket_content.add(createTicket_isvalid_label);
		 GridPane.setConstraints(createTicket_isvalid_label, 0, 4);
		 
		 createTicket_isvalid_t = new TextField();
		 createTicket_content.add(createTicket_isvalid_t);
		 createTicket_isvalid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createTicket_isvalid_t, 1, 4);
		 operationPanels.put("createTicket", createTicket);
		 
		 // ==================== GridPane_queryTicket ====================
		 GridPane queryTicket = new GridPane();
		 queryTicket.setHgap(4);
		 queryTicket.setVgap(6);
		 queryTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryTicket_content = queryTicket.getChildren();
		 Label queryTicket_ticketid_label = new Label("ticketid:");
		 queryTicket_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryTicket_content.add(queryTicket_ticketid_label);
		 GridPane.setConstraints(queryTicket_ticketid_label, 0, 0);
		 
		 queryTicket_ticketid_t = new TextField();
		 queryTicket_content.add(queryTicket_ticketid_t);
		 queryTicket_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryTicket_ticketid_t, 1, 0);
		 operationPanels.put("queryTicket", queryTicket);
		 
		 // ==================== GridPane_modifyTicket ====================
		 GridPane modifyTicket = new GridPane();
		 modifyTicket.setHgap(4);
		 modifyTicket.setVgap(6);
		 modifyTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyTicket_content = modifyTicket.getChildren();
		 Label modifyTicket_ticketid_label = new Label("ticketid:");
		 modifyTicket_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTicket_content.add(modifyTicket_ticketid_label);
		 GridPane.setConstraints(modifyTicket_ticketid_label, 0, 0);
		 
		 modifyTicket_ticketid_t = new TextField();
		 modifyTicket_content.add(modifyTicket_ticketid_t);
		 modifyTicket_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTicket_ticketid_t, 1, 0);
		 Label modifyTicket_routeid_label = new Label("routeid:");
		 modifyTicket_routeid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTicket_content.add(modifyTicket_routeid_label);
		 GridPane.setConstraints(modifyTicket_routeid_label, 0, 1);
		 
		 modifyTicket_routeid_t = new TextField();
		 modifyTicket_content.add(modifyTicket_routeid_t);
		 modifyTicket_routeid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTicket_routeid_t, 1, 1);
		 Label modifyTicket_seatid_label = new Label("seatid:");
		 modifyTicket_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTicket_content.add(modifyTicket_seatid_label);
		 GridPane.setConstraints(modifyTicket_seatid_label, 0, 2);
		 
		 modifyTicket_seatid_t = new TextField();
		 modifyTicket_content.add(modifyTicket_seatid_t);
		 modifyTicket_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTicket_seatid_t, 1, 2);
		 Label modifyTicket_price_label = new Label("price:");
		 modifyTicket_price_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTicket_content.add(modifyTicket_price_label);
		 GridPane.setConstraints(modifyTicket_price_label, 0, 3);
		 
		 modifyTicket_price_t = new TextField();
		 modifyTicket_content.add(modifyTicket_price_t);
		 modifyTicket_price_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTicket_price_t, 1, 3);
		 Label modifyTicket_isvalid_label = new Label("isvalid:");
		 modifyTicket_isvalid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyTicket_content.add(modifyTicket_isvalid_label);
		 GridPane.setConstraints(modifyTicket_isvalid_label, 0, 4);
		 
		 modifyTicket_isvalid_t = new TextField();
		 modifyTicket_content.add(modifyTicket_isvalid_t);
		 modifyTicket_isvalid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyTicket_isvalid_t, 1, 4);
		 operationPanels.put("modifyTicket", modifyTicket);
		 
		 // ==================== GridPane_deleteTicket ====================
		 GridPane deleteTicket = new GridPane();
		 deleteTicket.setHgap(4);
		 deleteTicket.setVgap(6);
		 deleteTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteTicket_content = deleteTicket.getChildren();
		 Label deleteTicket_ticketid_label = new Label("ticketid:");
		 deleteTicket_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteTicket_content.add(deleteTicket_ticketid_label);
		 GridPane.setConstraints(deleteTicket_ticketid_label, 0, 0);
		 
		 deleteTicket_ticketid_t = new TextField();
		 deleteTicket_content.add(deleteTicket_ticketid_t);
		 deleteTicket_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteTicket_ticketid_t, 1, 0);
		 operationPanels.put("deleteTicket", deleteTicket);
		 
		 // ==================== GridPane_createOrder ====================
		 GridPane createOrder = new GridPane();
		 createOrder.setHgap(4);
		 createOrder.setVgap(6);
		 createOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createOrder_content = createOrder.getChildren();
		 Label createOrder_orderid_label = new Label("orderid:");
		 createOrder_orderid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_orderid_label);
		 GridPane.setConstraints(createOrder_orderid_label, 0, 0);
		 
		 createOrder_orderid_t = new TextField();
		 createOrder_content.add(createOrder_orderid_t);
		 createOrder_orderid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_orderid_t, 1, 0);
		 Label createOrder_ticketid_label = new Label("ticketid:");
		 createOrder_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_ticketid_label);
		 GridPane.setConstraints(createOrder_ticketid_label, 0, 1);
		 
		 createOrder_ticketid_t = new TextField();
		 createOrder_content.add(createOrder_ticketid_t);
		 createOrder_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_ticketid_t, 1, 1);
		 Label createOrder_accoutid_label = new Label("accoutid:");
		 createOrder_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_accoutid_label);
		 GridPane.setConstraints(createOrder_accoutid_label, 0, 2);
		 
		 createOrder_accoutid_t = new TextField();
		 createOrder_content.add(createOrder_accoutid_t);
		 createOrder_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_accoutid_t, 1, 2);
		 Label createOrder_createtime_label = new Label("createtime:");
		 createOrder_createtime_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_createtime_label);
		 GridPane.setConstraints(createOrder_createtime_label, 0, 3);
		 
		 createOrder_createtime_t = new TextField();
		 createOrder_content.add(createOrder_createtime_t);
		 createOrder_createtime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_createtime_t, 1, 3);
		 Label createOrder_orderstatus_label = new Label("orderstatus:");
		 createOrder_orderstatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_orderstatus_label);
		 GridPane.setConstraints(createOrder_orderstatus_label, 0, 4);
		 
		 createOrder_orderstatus_cb = new ChoiceBox();
createOrder_orderstatus_cb.getItems().add("NOTPAID");
createOrder_orderstatus_cb.getItems().add("PAID");
createOrder_orderstatus_cb.getItems().add("CANCELED");
		 createOrder_orderstatus_cb.getSelectionModel().selectFirst();
		 createOrder_content.add(createOrder_orderstatus_cb);
		 GridPane.setConstraints(createOrder_orderstatus_cb, 1, 4);
		 operationPanels.put("createOrder", createOrder);
		 
		 // ==================== GridPane_queryOrder ====================
		 GridPane queryOrder = new GridPane();
		 queryOrder.setHgap(4);
		 queryOrder.setVgap(6);
		 queryOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryOrder_content = queryOrder.getChildren();
		 Label queryOrder_orderid_label = new Label("orderid:");
		 queryOrder_orderid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryOrder_content.add(queryOrder_orderid_label);
		 GridPane.setConstraints(queryOrder_orderid_label, 0, 0);
		 
		 queryOrder_orderid_t = new TextField();
		 queryOrder_content.add(queryOrder_orderid_t);
		 queryOrder_orderid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryOrder_orderid_t, 1, 0);
		 operationPanels.put("queryOrder", queryOrder);
		 
		 // ==================== GridPane_modifyOrder ====================
		 GridPane modifyOrder = new GridPane();
		 modifyOrder.setHgap(4);
		 modifyOrder.setVgap(6);
		 modifyOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyOrder_content = modifyOrder.getChildren();
		 Label modifyOrder_orderid_label = new Label("orderid:");
		 modifyOrder_orderid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_orderid_label);
		 GridPane.setConstraints(modifyOrder_orderid_label, 0, 0);
		 
		 modifyOrder_orderid_t = new TextField();
		 modifyOrder_content.add(modifyOrder_orderid_t);
		 modifyOrder_orderid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_orderid_t, 1, 0);
		 Label modifyOrder_ticketid_label = new Label("ticketid:");
		 modifyOrder_ticketid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_ticketid_label);
		 GridPane.setConstraints(modifyOrder_ticketid_label, 0, 1);
		 
		 modifyOrder_ticketid_t = new TextField();
		 modifyOrder_content.add(modifyOrder_ticketid_t);
		 modifyOrder_ticketid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_ticketid_t, 1, 1);
		 Label modifyOrder_accoutid_label = new Label("accoutid:");
		 modifyOrder_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_accoutid_label);
		 GridPane.setConstraints(modifyOrder_accoutid_label, 0, 2);
		 
		 modifyOrder_accoutid_t = new TextField();
		 modifyOrder_content.add(modifyOrder_accoutid_t);
		 modifyOrder_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_accoutid_t, 1, 2);
		 Label modifyOrder_createtime_label = new Label("createtime:");
		 modifyOrder_createtime_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_createtime_label);
		 GridPane.setConstraints(modifyOrder_createtime_label, 0, 3);
		 
		 modifyOrder_createtime_t = new TextField();
		 modifyOrder_content.add(modifyOrder_createtime_t);
		 modifyOrder_createtime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_createtime_t, 1, 3);
		 Label modifyOrder_orderstatus_label = new Label("orderstatus:");
		 modifyOrder_orderstatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_orderstatus_label);
		 GridPane.setConstraints(modifyOrder_orderstatus_label, 0, 4);
		 
		 modifyOrder_orderstatus_cb = new ChoiceBox();
modifyOrder_orderstatus_cb.getItems().add("NOTPAID");
modifyOrder_orderstatus_cb.getItems().add("PAID");
modifyOrder_orderstatus_cb.getItems().add("CANCELED");
		 modifyOrder_orderstatus_cb.getSelectionModel().selectFirst();
		 modifyOrder_content.add(modifyOrder_orderstatus_cb);
		 GridPane.setConstraints(modifyOrder_orderstatus_cb, 1, 4);
		 operationPanels.put("modifyOrder", modifyOrder);
		 
		 // ==================== GridPane_deleteOrder ====================
		 GridPane deleteOrder = new GridPane();
		 deleteOrder.setHgap(4);
		 deleteOrder.setVgap(6);
		 deleteOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteOrder_content = deleteOrder.getChildren();
		 Label deleteOrder_orderid_label = new Label("orderid:");
		 deleteOrder_orderid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteOrder_content.add(deleteOrder_orderid_label);
		 GridPane.setConstraints(deleteOrder_orderid_label, 0, 0);
		 
		 deleteOrder_orderid_t = new TextField();
		 deleteOrder_content.add(deleteOrder_orderid_t);
		 deleteOrder_orderid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteOrder_orderid_t, 1, 0);
		 operationPanels.put("deleteOrder", deleteOrder);
		 
		 // ==================== GridPane_createAccout ====================
		 GridPane createAccout = new GridPane();
		 createAccout.setHgap(4);
		 createAccout.setVgap(6);
		 createAccout.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createAccout_content = createAccout.getChildren();
		 Label createAccout_accoutid_label = new Label("accoutid:");
		 createAccout_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createAccout_content.add(createAccout_accoutid_label);
		 GridPane.setConstraints(createAccout_accoutid_label, 0, 0);
		 
		 createAccout_accoutid_t = new TextField();
		 createAccout_content.add(createAccout_accoutid_t);
		 createAccout_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createAccout_accoutid_t, 1, 0);
		 Label createAccout_name_label = new Label("name:");
		 createAccout_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createAccout_content.add(createAccout_name_label);
		 GridPane.setConstraints(createAccout_name_label, 0, 1);
		 
		 createAccout_name_t = new TextField();
		 createAccout_content.add(createAccout_name_t);
		 createAccout_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createAccout_name_t, 1, 1);
		 Label createAccout_phonenumber_label = new Label("phonenumber:");
		 createAccout_phonenumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 createAccout_content.add(createAccout_phonenumber_label);
		 GridPane.setConstraints(createAccout_phonenumber_label, 0, 2);
		 
		 createAccout_phonenumber_t = new TextField();
		 createAccout_content.add(createAccout_phonenumber_t);
		 createAccout_phonenumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createAccout_phonenumber_t, 1, 2);
		 operationPanels.put("createAccout", createAccout);
		 
		 // ==================== GridPane_queryAccout ====================
		 GridPane queryAccout = new GridPane();
		 queryAccout.setHgap(4);
		 queryAccout.setVgap(6);
		 queryAccout.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryAccout_content = queryAccout.getChildren();
		 Label queryAccout_accoutid_label = new Label("accoutid:");
		 queryAccout_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryAccout_content.add(queryAccout_accoutid_label);
		 GridPane.setConstraints(queryAccout_accoutid_label, 0, 0);
		 
		 queryAccout_accoutid_t = new TextField();
		 queryAccout_content.add(queryAccout_accoutid_t);
		 queryAccout_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryAccout_accoutid_t, 1, 0);
		 operationPanels.put("queryAccout", queryAccout);
		 
		 // ==================== GridPane_modifyAccout ====================
		 GridPane modifyAccout = new GridPane();
		 modifyAccout.setHgap(4);
		 modifyAccout.setVgap(6);
		 modifyAccout.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyAccout_content = modifyAccout.getChildren();
		 Label modifyAccout_accoutid_label = new Label("accoutid:");
		 modifyAccout_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyAccout_content.add(modifyAccout_accoutid_label);
		 GridPane.setConstraints(modifyAccout_accoutid_label, 0, 0);
		 
		 modifyAccout_accoutid_t = new TextField();
		 modifyAccout_content.add(modifyAccout_accoutid_t);
		 modifyAccout_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyAccout_accoutid_t, 1, 0);
		 Label modifyAccout_name_label = new Label("name:");
		 modifyAccout_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyAccout_content.add(modifyAccout_name_label);
		 GridPane.setConstraints(modifyAccout_name_label, 0, 1);
		 
		 modifyAccout_name_t = new TextField();
		 modifyAccout_content.add(modifyAccout_name_t);
		 modifyAccout_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyAccout_name_t, 1, 1);
		 Label modifyAccout_phonenumber_label = new Label("phonenumber:");
		 modifyAccout_phonenumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyAccout_content.add(modifyAccout_phonenumber_label);
		 GridPane.setConstraints(modifyAccout_phonenumber_label, 0, 2);
		 
		 modifyAccout_phonenumber_t = new TextField();
		 modifyAccout_content.add(modifyAccout_phonenumber_t);
		 modifyAccout_phonenumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyAccout_phonenumber_t, 1, 2);
		 operationPanels.put("modifyAccout", modifyAccout);
		 
		 // ==================== GridPane_deleteAccout ====================
		 GridPane deleteAccout = new GridPane();
		 deleteAccout.setHgap(4);
		 deleteAccout.setVgap(6);
		 deleteAccout.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteAccout_content = deleteAccout.getChildren();
		 Label deleteAccout_accoutid_label = new Label("accoutid:");
		 deleteAccout_accoutid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteAccout_content.add(deleteAccout_accoutid_label);
		 GridPane.setConstraints(deleteAccout_accoutid_label, 0, 0);
		 
		 deleteAccout_accoutid_t = new TextField();
		 deleteAccout_content.add(deleteAccout_accoutid_t);
		 deleteAccout_accoutid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteAccout_accoutid_t, 1, 0);
		 operationPanels.put("deleteAccout", deleteAccout);
		 
		 // ==================== GridPane_createSeat ====================
		 GridPane createSeat = new GridPane();
		 createSeat.setHgap(4);
		 createSeat.setVgap(6);
		 createSeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createSeat_content = createSeat.getChildren();
		 Label createSeat_seatid_label = new Label("seatid:");
		 createSeat_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createSeat_content.add(createSeat_seatid_label);
		 GridPane.setConstraints(createSeat_seatid_label, 0, 0);
		 
		 createSeat_seatid_t = new TextField();
		 createSeat_content.add(createSeat_seatid_t);
		 createSeat_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createSeat_seatid_t, 1, 0);
		 Label createSeat_trainid_label = new Label("trainid:");
		 createSeat_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createSeat_content.add(createSeat_trainid_label);
		 GridPane.setConstraints(createSeat_trainid_label, 0, 1);
		 
		 createSeat_trainid_t = new TextField();
		 createSeat_content.add(createSeat_trainid_t);
		 createSeat_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createSeat_trainid_t, 1, 1);
		 Label createSeat_seattype_label = new Label("seattype:");
		 createSeat_seattype_label.setMinWidth(Region.USE_PREF_SIZE);
		 createSeat_content.add(createSeat_seattype_label);
		 GridPane.setConstraints(createSeat_seattype_label, 0, 2);
		 
		 createSeat_seattype_cb = new ChoiceBox();
createSeat_seattype_cb.getItems().add("HARDBED");
createSeat_seattype_cb.getItems().add("SOFTBED");
createSeat_seattype_cb.getItems().add("SEAT");
createSeat_seattype_cb.getItems().add("STAND");
		 createSeat_seattype_cb.getSelectionModel().selectFirst();
		 createSeat_content.add(createSeat_seattype_cb);
		 GridPane.setConstraints(createSeat_seattype_cb, 1, 2);
		 operationPanels.put("createSeat", createSeat);
		 
		 // ==================== GridPane_querySeat ====================
		 GridPane querySeat = new GridPane();
		 querySeat.setHgap(4);
		 querySeat.setVgap(6);
		 querySeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> querySeat_content = querySeat.getChildren();
		 Label querySeat_seatid_label = new Label("seatid:");
		 querySeat_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 querySeat_content.add(querySeat_seatid_label);
		 GridPane.setConstraints(querySeat_seatid_label, 0, 0);
		 
		 querySeat_seatid_t = new TextField();
		 querySeat_content.add(querySeat_seatid_t);
		 querySeat_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(querySeat_seatid_t, 1, 0);
		 operationPanels.put("querySeat", querySeat);
		 
		 // ==================== GridPane_modifySeat ====================
		 GridPane modifySeat = new GridPane();
		 modifySeat.setHgap(4);
		 modifySeat.setVgap(6);
		 modifySeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifySeat_content = modifySeat.getChildren();
		 Label modifySeat_seatid_label = new Label("seatid:");
		 modifySeat_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifySeat_content.add(modifySeat_seatid_label);
		 GridPane.setConstraints(modifySeat_seatid_label, 0, 0);
		 
		 modifySeat_seatid_t = new TextField();
		 modifySeat_content.add(modifySeat_seatid_t);
		 modifySeat_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifySeat_seatid_t, 1, 0);
		 Label modifySeat_trainid_label = new Label("trainid:");
		 modifySeat_trainid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifySeat_content.add(modifySeat_trainid_label);
		 GridPane.setConstraints(modifySeat_trainid_label, 0, 1);
		 
		 modifySeat_trainid_t = new TextField();
		 modifySeat_content.add(modifySeat_trainid_t);
		 modifySeat_trainid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifySeat_trainid_t, 1, 1);
		 Label modifySeat_seattype_label = new Label("seattype:");
		 modifySeat_seattype_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifySeat_content.add(modifySeat_seattype_label);
		 GridPane.setConstraints(modifySeat_seattype_label, 0, 2);
		 
		 modifySeat_seattype_cb = new ChoiceBox();
modifySeat_seattype_cb.getItems().add("HARDBED");
modifySeat_seattype_cb.getItems().add("SOFTBED");
modifySeat_seattype_cb.getItems().add("SEAT");
modifySeat_seattype_cb.getItems().add("STAND");
		 modifySeat_seattype_cb.getSelectionModel().selectFirst();
		 modifySeat_content.add(modifySeat_seattype_cb);
		 GridPane.setConstraints(modifySeat_seattype_cb, 1, 2);
		 operationPanels.put("modifySeat", modifySeat);
		 
		 // ==================== GridPane_deleteSeat ====================
		 GridPane deleteSeat = new GridPane();
		 deleteSeat.setHgap(4);
		 deleteSeat.setVgap(6);
		 deleteSeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteSeat_content = deleteSeat.getChildren();
		 Label deleteSeat_seatid_label = new Label("seatid:");
		 deleteSeat_seatid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteSeat_content.add(deleteSeat_seatid_label);
		 GridPane.setConstraints(deleteSeat_seatid_label, 0, 0);
		 
		 deleteSeat_seatid_t = new TextField();
		 deleteSeat_content.add(deleteSeat_seatid_t);
		 deleteSeat_seatid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteSeat_seatid_t, 1, 0);
		 operationPanels.put("deleteSeat", deleteSeat);
		 
		 // ==================== GridPane_buyTicket ====================
		 GridPane buyTicket = new GridPane();
		 buyTicket.setHgap(4);
		 buyTicket.setVgap(6);
		 buyTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> buyTicket_content = buyTicket.getChildren();
		 Label buyTicket_accoutId_label = new Label("accoutId:");
		 buyTicket_accoutId_label.setMinWidth(Region.USE_PREF_SIZE);
		 buyTicket_content.add(buyTicket_accoutId_label);
		 GridPane.setConstraints(buyTicket_accoutId_label, 0, 0);
		 
		 buyTicket_accoutId_t = new TextField();
		 buyTicket_content.add(buyTicket_accoutId_t);
		 buyTicket_accoutId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(buyTicket_accoutId_t, 1, 0);
		 Label buyTicket_routeId_label = new Label("routeId:");
		 buyTicket_routeId_label.setMinWidth(Region.USE_PREF_SIZE);
		 buyTicket_content.add(buyTicket_routeId_label);
		 GridPane.setConstraints(buyTicket_routeId_label, 0, 1);
		 
		 buyTicket_routeId_t = new TextField();
		 buyTicket_content.add(buyTicket_routeId_t);
		 buyTicket_routeId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(buyTicket_routeId_t, 1, 1);
		 Label buyTicket_ticketId_label = new Label("ticketId:");
		 buyTicket_ticketId_label.setMinWidth(Region.USE_PREF_SIZE);
		 buyTicket_content.add(buyTicket_ticketId_label);
		 GridPane.setConstraints(buyTicket_ticketId_label, 0, 2);
		 
		 buyTicket_ticketId_t = new TextField();
		 buyTicket_content.add(buyTicket_ticketId_t);
		 buyTicket_ticketId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(buyTicket_ticketId_t, 1, 2);
		 operationPanels.put("buyTicket", buyTicket);
		 
		 // ==================== GridPane_saveOrder ====================
		 GridPane saveOrder = new GridPane();
		 saveOrder.setHgap(4);
		 saveOrder.setVgap(6);
		 saveOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> saveOrder_content = saveOrder.getChildren();
		 Label saveOrder_orderId_label = new Label("orderId:");
		 saveOrder_orderId_label.setMinWidth(Region.USE_PREF_SIZE);
		 saveOrder_content.add(saveOrder_orderId_label);
		 GridPane.setConstraints(saveOrder_orderId_label, 0, 0);
		 
		 saveOrder_orderId_t = new TextField();
		 saveOrder_content.add(saveOrder_orderId_t);
		 saveOrder_orderId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(saveOrder_orderId_t, 1, 0);
		 Label saveOrder_boughtTime_label = new Label("boughtTime:");
		 saveOrder_boughtTime_label.setMinWidth(Region.USE_PREF_SIZE);
		 saveOrder_content.add(saveOrder_boughtTime_label);
		 GridPane.setConstraints(saveOrder_boughtTime_label, 0, 1);
		 
		 saveOrder_boughtTime_t = new TextField();
		 saveOrder_content.add(saveOrder_boughtTime_t);
		 saveOrder_boughtTime_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(saveOrder_boughtTime_t, 1, 1);
		 operationPanels.put("saveOrder", saveOrder);
		 
		 // ==================== GridPane_showSeats ====================
		 GridPane showSeats = new GridPane();
		 showSeats.setHgap(4);
		 showSeats.setVgap(6);
		 showSeats.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> showSeats_content = showSeats.getChildren();
		 Label showSeats_label = new Label("This operation is no intput parameters..");
		 showSeats_label.setMinWidth(Region.USE_PREF_SIZE);
		 showSeats_content.add(showSeats_label);
		 GridPane.setConstraints(showSeats_label, 0, 0);
		 operationPanels.put("showSeats", showSeats);
		 
		 // ==================== GridPane_selectSeat ====================
		 GridPane selectSeat = new GridPane();
		 selectSeat.setHgap(4);
		 selectSeat.setVgap(6);
		 selectSeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> selectSeat_content = selectSeat.getChildren();
		 Label selectSeat_seatId_label = new Label("seatId:");
		 selectSeat_seatId_label.setMinWidth(Region.USE_PREF_SIZE);
		 selectSeat_content.add(selectSeat_seatId_label);
		 GridPane.setConstraints(selectSeat_seatId_label, 0, 0);
		 
		 selectSeat_seatId_t = new TextField();
		 selectSeat_content.add(selectSeat_seatId_t);
		 selectSeat_seatId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(selectSeat_seatId_t, 1, 0);
		 operationPanels.put("selectSeat", selectSeat);
		 
		 // ==================== GridPane_pay ====================
		 GridPane pay = new GridPane();
		 pay.setHgap(4);
		 pay.setVgap(6);
		 pay.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> pay_content = pay.getChildren();
		 Label pay_price_label = new Label("price:");
		 pay_price_label.setMinWidth(Region.USE_PREF_SIZE);
		 pay_content.add(pay_price_label);
		 GridPane.setConstraints(pay_price_label, 0, 0);
		 
		 pay_price_t = new TextField();
		 pay_content.add(pay_price_t);
		 pay_price_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(pay_price_t, 1, 0);
		 operationPanels.put("pay", pay);
		 
		 // ==================== GridPane_queryInformation ====================
		 GridPane queryInformation = new GridPane();
		 queryInformation.setHgap(4);
		 queryInformation.setVgap(6);
		 queryInformation.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryInformation_content = queryInformation.getChildren();
		 Label queryInformation_startStation_label = new Label("startStation:");
		 queryInformation_startStation_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryInformation_content.add(queryInformation_startStation_label);
		 GridPane.setConstraints(queryInformation_startStation_label, 0, 0);
		 
		 queryInformation_startStation_t = new TextField();
		 queryInformation_content.add(queryInformation_startStation_t);
		 queryInformation_startStation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryInformation_startStation_t, 1, 0);
		 Label queryInformation_endStation_label = new Label("endStation:");
		 queryInformation_endStation_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryInformation_content.add(queryInformation_endStation_label);
		 GridPane.setConstraints(queryInformation_endStation_label, 0, 1);
		 
		 queryInformation_endStation_t = new TextField();
		 queryInformation_content.add(queryInformation_endStation_t);
		 queryInformation_endStation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryInformation_endStation_t, 1, 1);
		 Label queryInformation_time_label = new Label("time:");
		 queryInformation_time_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryInformation_content.add(queryInformation_time_label);
		 GridPane.setConstraints(queryInformation_time_label, 0, 2);
		 
		 queryInformation_time_t = new TextField();
		 queryInformation_content.add(queryInformation_time_t);
		 queryInformation_time_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryInformation_time_t, 1, 2);
		 operationPanels.put("queryInformation", queryInformation);
		 
		 // ==================== GridPane_cancelTrip ====================
		 GridPane cancelTrip = new GridPane();
		 cancelTrip.setHgap(4);
		 cancelTrip.setVgap(6);
		 cancelTrip.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> cancelTrip_content = cancelTrip.getChildren();
		 Label cancelTrip_ticketId_label = new Label("ticketId:");
		 cancelTrip_ticketId_label.setMinWidth(Region.USE_PREF_SIZE);
		 cancelTrip_content.add(cancelTrip_ticketId_label);
		 GridPane.setConstraints(cancelTrip_ticketId_label, 0, 0);
		 
		 cancelTrip_ticketId_t = new TextField();
		 cancelTrip_content.add(cancelTrip_ticketId_t);
		 cancelTrip_ticketId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(cancelTrip_ticketId_t, 1, 0);
		 Label cancelTrip_accoutId_label = new Label("accoutId:");
		 cancelTrip_accoutId_label.setMinWidth(Region.USE_PREF_SIZE);
		 cancelTrip_content.add(cancelTrip_accoutId_label);
		 GridPane.setConstraints(cancelTrip_accoutId_label, 0, 1);
		 
		 cancelTrip_accoutId_t = new TextField();
		 cancelTrip_content.add(cancelTrip_accoutId_t);
		 cancelTrip_accoutId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(cancelTrip_accoutId_t, 1, 1);
		 operationPanels.put("cancelTrip", cancelTrip);
		 
		 // ==================== GridPane_queryByAccoutId ====================
		 GridPane queryByAccoutId = new GridPane();
		 queryByAccoutId.setHgap(4);
		 queryByAccoutId.setVgap(6);
		 queryByAccoutId.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryByAccoutId_content = queryByAccoutId.getChildren();
		 Label queryByAccoutId_accoutId_label = new Label("accoutId:");
		 queryByAccoutId_accoutId_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryByAccoutId_content.add(queryByAccoutId_accoutId_label);
		 GridPane.setConstraints(queryByAccoutId_accoutId_label, 0, 0);
		 
		 queryByAccoutId_accoutId_t = new TextField();
		 queryByAccoutId_content.add(queryByAccoutId_accoutId_t);
		 queryByAccoutId_accoutId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryByAccoutId_accoutId_t, 1, 0);
		 operationPanels.put("queryByAccoutId", queryByAccoutId);
		 
		 // ==================== GridPane_queryTicketId ====================
		 GridPane queryTicketId = new GridPane();
		 queryTicketId.setHgap(4);
		 queryTicketId.setVgap(6);
		 queryTicketId.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryTicketId_content = queryTicketId.getChildren();
		 Label queryTicketId_ticketId_label = new Label("ticketId:");
		 queryTicketId_ticketId_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryTicketId_content.add(queryTicketId_ticketId_label);
		 GridPane.setConstraints(queryTicketId_ticketId_label, 0, 0);
		 
		 queryTicketId_ticketId_t = new TextField();
		 queryTicketId_content.add(queryTicketId_ticketId_t);
		 queryTicketId_ticketId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryTicketId_ticketId_t, 1, 0);
		 operationPanels.put("queryTicketId", queryTicketId);
		 
		 // ==================== GridPane_updateTicket ====================
		 GridPane updateTicket = new GridPane();
		 updateTicket.setHgap(4);
		 updateTicket.setVgap(6);
		 updateTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> updateTicket_content = updateTicket.getChildren();
		 Label updateTicket_accoutId_label = new Label("accoutId:");
		 updateTicket_accoutId_label.setMinWidth(Region.USE_PREF_SIZE);
		 updateTicket_content.add(updateTicket_accoutId_label);
		 GridPane.setConstraints(updateTicket_accoutId_label, 0, 0);
		 
		 updateTicket_accoutId_t = new TextField();
		 updateTicket_content.add(updateTicket_accoutId_t);
		 updateTicket_accoutId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(updateTicket_accoutId_t, 1, 0);
		 Label updateTicket_ticketId_label = new Label("ticketId:");
		 updateTicket_ticketId_label.setMinWidth(Region.USE_PREF_SIZE);
		 updateTicket_content.add(updateTicket_ticketId_label);
		 GridPane.setConstraints(updateTicket_ticketId_label, 0, 1);
		 
		 updateTicket_ticketId_t = new TextField();
		 updateTicket_content.add(updateTicket_ticketId_t);
		 updateTicket_ticketId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(updateTicket_ticketId_t, 1, 1);
		 Label updateTicket_newRouteId_label = new Label("newRouteId:");
		 updateTicket_newRouteId_label.setMinWidth(Region.USE_PREF_SIZE);
		 updateTicket_content.add(updateTicket_newRouteId_label);
		 GridPane.setConstraints(updateTicket_newRouteId_label, 0, 2);
		 
		 updateTicket_newRouteId_t = new TextField();
		 updateTicket_content.add(updateTicket_newRouteId_t);
		 updateTicket_newRouteId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(updateTicket_newRouteId_t, 1, 2);
		 operationPanels.put("updateTicket", updateTicket);
		 
		 // ==================== GridPane_updateOrder ====================
		 GridPane updateOrder = new GridPane();
		 updateOrder.setHgap(4);
		 updateOrder.setVgap(6);
		 updateOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> updateOrder_content = updateOrder.getChildren();
		 Label updateOrder_time_label = new Label("time:");
		 updateOrder_time_label.setMinWidth(Region.USE_PREF_SIZE);
		 updateOrder_content.add(updateOrder_time_label);
		 GridPane.setConstraints(updateOrder_time_label, 0, 0);
		 
		 updateOrder_time_t = new TextField();
		 updateOrder_content.add(updateOrder_time_t);
		 updateOrder_time_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(updateOrder_time_t, 1, 0);
		 operationPanels.put("updateOrder", updateOrder);
		 
		 // ==================== GridPane_showSeatsByRouteId ====================
		 GridPane showSeatsByRouteId = new GridPane();
		 showSeatsByRouteId.setHgap(4);
		 showSeatsByRouteId.setVgap(6);
		 showSeatsByRouteId.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> showSeatsByRouteId_content = showSeatsByRouteId.getChildren();
		 Label showSeatsByRouteId_label = new Label("This operation is no intput parameters..");
		 showSeatsByRouteId_label.setMinWidth(Region.USE_PREF_SIZE);
		 showSeatsByRouteId_content.add(showSeatsByRouteId_label);
		 GridPane.setConstraints(showSeatsByRouteId_label, 0, 0);
		 operationPanels.put("showSeatsByRouteId", showSeatsByRouteId);
		 
		 // ==================== GridPane_selectNewSeat ====================
		 GridPane selectNewSeat = new GridPane();
		 selectNewSeat.setHgap(4);
		 selectNewSeat.setVgap(6);
		 selectNewSeat.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> selectNewSeat_content = selectNewSeat.getChildren();
		 Label selectNewSeat_seatId_label = new Label("seatId:");
		 selectNewSeat_seatId_label.setMinWidth(Region.USE_PREF_SIZE);
		 selectNewSeat_content.add(selectNewSeat_seatId_label);
		 GridPane.setConstraints(selectNewSeat_seatId_label, 0, 0);
		 
		 selectNewSeat_seatId_t = new TextField();
		 selectNewSeat_content.add(selectNewSeat_seatId_t);
		 selectNewSeat_seatId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(selectNewSeat_seatId_t, 1, 0);
		 operationPanels.put("selectNewSeat", selectNewSeat);
		 
		 // ==================== GridPane_payDifference ====================
		 GridPane payDifference = new GridPane();
		 payDifference.setHgap(4);
		 payDifference.setVgap(6);
		 payDifference.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> payDifference_content = payDifference.getChildren();
		 Label payDifference_difference_label = new Label("difference:");
		 payDifference_difference_label.setMinWidth(Region.USE_PREF_SIZE);
		 payDifference_content.add(payDifference_difference_label);
		 GridPane.setConstraints(payDifference_difference_label, 0, 0);
		 
		 payDifference_difference_t = new TextField();
		 payDifference_content.add(payDifference_difference_t);
		 payDifference_difference_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(payDifference_difference_t, 1, 0);
		 operationPanels.put("payDifference", payDifference);
		 
		 // ==================== GridPane_deleteOverdueTicket ====================
		 GridPane deleteOverdueTicket = new GridPane();
		 deleteOverdueTicket.setHgap(4);
		 deleteOverdueTicket.setVgap(6);
		 deleteOverdueTicket.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteOverdueTicket_content = deleteOverdueTicket.getChildren();
		 Label deleteOverdueTicket_ticketId_label = new Label("ticketId:");
		 deleteOverdueTicket_ticketId_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteOverdueTicket_content.add(deleteOverdueTicket_ticketId_label);
		 GridPane.setConstraints(deleteOverdueTicket_ticketId_label, 0, 0);
		 
		 deleteOverdueTicket_ticketId_t = new TextField();
		 deleteOverdueTicket_content.add(deleteOverdueTicket_ticketId_t);
		 deleteOverdueTicket_ticketId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteOverdueTicket_ticketId_t, 1, 0);
		 operationPanels.put("deleteOverdueTicket", deleteOverdueTicket);
		 
		 // ==================== GridPane_deleteOverdueOrder ====================
		 GridPane deleteOverdueOrder = new GridPane();
		 deleteOverdueOrder.setHgap(4);
		 deleteOverdueOrder.setVgap(6);
		 deleteOverdueOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteOverdueOrder_content = deleteOverdueOrder.getChildren();
		 Label deleteOverdueOrder_label = new Label("This operation is no intput parameters..");
		 deleteOverdueOrder_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteOverdueOrder_content.add(deleteOverdueOrder_label);
		 GridPane.setConstraints(deleteOverdueOrder_label, 0, 0);
		 operationPanels.put("deleteOverdueOrder", deleteOverdueOrder);
		 
		 // ==================== GridPane_sendNotification ====================
		 GridPane sendNotification = new GridPane();
		 sendNotification.setHgap(4);
		 sendNotification.setVgap(6);
		 sendNotification.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> sendNotification_content = sendNotification.getChildren();
		 Label sendNotification_phoneNumber_label = new Label("phoneNumber:");
		 sendNotification_phoneNumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 sendNotification_content.add(sendNotification_phoneNumber_label);
		 GridPane.setConstraints(sendNotification_phoneNumber_label, 0, 0);
		 
		 sendNotification_phoneNumber_t = new TextField();
		 sendNotification_content.add(sendNotification_phoneNumber_t);
		 sendNotification_phoneNumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(sendNotification_phoneNumber_t, 1, 0);
		 operationPanels.put("sendNotification", sendNotification);
		 
	}	

	public void actorTreeViewBinding() {
		
		 
		
		
		 
		TreeItem<String> treeRootpassenger = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_buyTicket = new TreeItem<String>("buyTicket");
			subTreeRoot_buyTicket.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("buyTicket"),
					 	new TreeItem<String>("saveOrder"),
					 	new TreeItem<String>("showSeats"),
					 	new TreeItem<String>("selectSeat"),
					 	new TreeItem<String>("pay")
				 		));	
			TreeItem<String> subTreeRoot_modifyTrip = new TreeItem<String>("modifyTrip");
			subTreeRoot_modifyTrip.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("updateTicket"),
					 	new TreeItem<String>("updateOrder"),
					 	new TreeItem<String>("showSeatsByRouteId"),
					 	new TreeItem<String>("selectNewSeat"),
					 	new TreeItem<String>("payDifference")
				 		));	
		
		treeRootpassenger.getChildren().addAll(Arrays.asList(
			subTreeRoot_buyTicket,
			new TreeItem<String>("queryInformation"),
			new TreeItem<String>("cancelTrip"),
			subTreeRoot_modifyTrip
					));
		
		treeRootpassenger.setExpanded(true);

		actor_treeview_passenger.setShowRoot(false);
		actor_treeview_passenger.setRoot(treeRootpassenger);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_passenger.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootthirdsystem = new TreeItem<String>("Root node");
		
		treeRootthirdsystem.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("sendNotification")
					));
		
		treeRootthirdsystem.setExpanded(true);

		actor_treeview_thirdsystem.setShowRoot(false);
		actor_treeview_thirdsystem.setRoot(treeRootthirdsystem);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_thirdsystem.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootstaff = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_manageRoute = new TreeItem<String>("manageRoute");
			subTreeRoot_manageRoute.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createRoute"),
					 	new TreeItem<String>("queryRoute"),
					 	new TreeItem<String>("modifyRoute"),
					 	new TreeItem<String>("deleteRoute")
				 		));	
			TreeItem<String> subTreeRoot_manageTrain = new TreeItem<String>("manageTrain");
			subTreeRoot_manageTrain.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createTrain"),
					 	new TreeItem<String>("queryTrain"),
					 	new TreeItem<String>("modifyTrain"),
					 	new TreeItem<String>("deleteTrain")
				 		));	
			TreeItem<String> subTreeRoot_manageSeat = new TreeItem<String>("manageSeat");
			subTreeRoot_manageSeat.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createSeat"),
					 	new TreeItem<String>("querySeat"),
					 	new TreeItem<String>("modifySeat"),
					 	new TreeItem<String>("deleteSeat")
				 		));	
		
		treeRootstaff.getChildren().addAll(Arrays.asList(
			subTreeRoot_manageRoute,
			subTreeRoot_manageTrain,
			subTreeRoot_manageSeat
					));
		
		treeRootstaff.setExpanded(true);

		actor_treeview_staff.setShowRoot(false);
		actor_treeview_staff.setRoot(treeRootstaff);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_staff.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootadministrator = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_queryOrder = new TreeItem<String>("queryOrder");
			subTreeRoot_queryOrder.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("queryByAccoutId"),
					 	new TreeItem<String>("queryTicketId")
				 		));	
			TreeItem<String> subTreeRoot_manageAccout = new TreeItem<String>("manageAccout");
			subTreeRoot_manageAccout.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("createAccout"),
					 	new TreeItem<String>("queryAccout"),
					 	new TreeItem<String>("modifyAccout"),
					 	new TreeItem<String>("deleteAccout")
				 		));	
			TreeItem<String> subTreeRoot_deleteOverdueOrder = new TreeItem<String>("deleteOverdueOrder");
			subTreeRoot_deleteOverdueOrder.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("deleteOverdueTicket"),
					 	new TreeItem<String>("deleteOverdueOrder")
				 		));	
		
		treeRootadministrator.getChildren().addAll(Arrays.asList(
			subTreeRoot_queryOrder,
			subTreeRoot_manageAccout,
			subTreeRoot_deleteOverdueOrder
					));
		
		treeRootadministrator.setExpanded(true);

		actor_treeview_administrator.setShowRoot(false);
		actor_treeview_administrator.setRoot(treeRootadministrator);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_administrator.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
	}

	/**
	*    Execute Operation
	*/
	@FXML
	public void execute(ActionEvent event) {
		
		switch (clickedOp) {
		case "createRoute" : createRoute(); break;
		case "queryRoute" : queryRoute(); break;
		case "modifyRoute" : modifyRoute(); break;
		case "deleteRoute" : deleteRoute(); break;
		case "createTrain" : createTrain(); break;
		case "queryTrain" : queryTrain(); break;
		case "modifyTrain" : modifyTrain(); break;
		case "deleteTrain" : deleteTrain(); break;
		case "createTicket" : createTicket(); break;
		case "queryTicket" : queryTicket(); break;
		case "modifyTicket" : modifyTicket(); break;
		case "deleteTicket" : deleteTicket(); break;
		case "createOrder" : createOrder(); break;
		case "queryOrder" : queryOrder(); break;
		case "modifyOrder" : modifyOrder(); break;
		case "deleteOrder" : deleteOrder(); break;
		case "createAccout" : createAccout(); break;
		case "queryAccout" : queryAccout(); break;
		case "modifyAccout" : modifyAccout(); break;
		case "deleteAccout" : deleteAccout(); break;
		case "createSeat" : createSeat(); break;
		case "querySeat" : querySeat(); break;
		case "modifySeat" : modifySeat(); break;
		case "deleteSeat" : deleteSeat(); break;
		case "buyTicket" : buyTicket(); break;
		case "saveOrder" : saveOrder(); break;
		case "showSeats" : showSeats(); break;
		case "selectSeat" : selectSeat(); break;
		case "pay" : pay(); break;
		case "queryInformation" : queryInformation(); break;
		case "cancelTrip" : cancelTrip(); break;
		case "queryByAccoutId" : queryByAccoutId(); break;
		case "queryTicketId" : queryTicketId(); break;
		case "updateTicket" : updateTicket(); break;
		case "updateOrder" : updateOrder(); break;
		case "showSeatsByRouteId" : showSeatsByRouteId(); break;
		case "selectNewSeat" : selectNewSeat(); break;
		case "payDifference" : payDifference(); break;
		case "deleteOverdueTicket" : deleteOverdueTicket(); break;
		case "deleteOverdueOrder" : deleteOverdueOrder(); break;
		case "sendNotification" : sendNotification(); break;
		
		}
		
		System.out.println("execute buttion clicked");
		
		//checking relevant invariants
		opInvairantPanelUpdate();
	}

	/**
	*    Refresh All
	*/		
	@FXML
	public void refresh(ActionEvent event) {
		
		refreshAll();
		System.out.println("refresh all");
	}		
	
	/**
	*    Save All
	*/			
	@FXML
	public void save(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save State to File");
		fileChooser.setInitialFileName("*.state");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			System.out.println("save state to file " + file.getAbsolutePath());				
			EntityManager.save(file);
		}
	}
	
	/**
	*    Load All
	*/			
	@FXML
	public void load(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open State File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			System.out.println("choose file" + file.getAbsolutePath());
			EntityManager.load(file); 
		}
		
		//refresh GUI after load data
		refreshAll();
	}
	
	
	//precondition unsat dialog
	public void preconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Precondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}
	
	//postcondition unsat dialog
	public void postconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Postcondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}

	public void thirdpartyServiceUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("third party service is exception");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}		
	
	
	public void createRoute() {
		
		System.out.println("execute createRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createRoute in service: ManageRouteCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageroutecrudservice_service.createRoute(
			createRoute_routeid_t.getText(),
			createRoute_startstation_t.getText(),
			createRoute_endstation_t.getText(),
			createRoute_time_t.getText(),
			createRoute_trainid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryRoute() {
		
		System.out.println("execute queryRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryRoute in service: ManageRouteCRUDService ");
		
		try {
			//invoke op with parameters
				Route r = manageroutecrudservice_service.queryRoute(
				queryRoute_routeid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableRoute = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableRoute_RouteId = new TableColumn<Map<String, String>, String>("RouteId");
				tableRoute_RouteId.setMinWidth("RouteId".length()*10);
				tableRoute_RouteId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("RouteId"));
				    }
				});	
				tableRoute.getColumns().add(tableRoute_RouteId);
				TableColumn<Map<String, String>, String> tableRoute_StartStation = new TableColumn<Map<String, String>, String>("StartStation");
				tableRoute_StartStation.setMinWidth("StartStation".length()*10);
				tableRoute_StartStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("StartStation"));
				    }
				});	
				tableRoute.getColumns().add(tableRoute_StartStation);
				TableColumn<Map<String, String>, String> tableRoute_EndStation = new TableColumn<Map<String, String>, String>("EndStation");
				tableRoute_EndStation.setMinWidth("EndStation".length()*10);
				tableRoute_EndStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("EndStation"));
				    }
				});	
				tableRoute.getColumns().add(tableRoute_EndStation);
				TableColumn<Map<String, String>, String> tableRoute_Time = new TableColumn<Map<String, String>, String>("Time");
				tableRoute_Time.setMinWidth("Time".length()*10);
				tableRoute_Time.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Time"));
				    }
				});	
				tableRoute.getColumns().add(tableRoute_Time);
				TableColumn<Map<String, String>, String> tableRoute_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
				tableRoute_TrainId.setMinWidth("TrainId".length()*10);
				tableRoute_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
				    }
				});	
				tableRoute.getColumns().add(tableRoute_TrainId);
				
				ObservableList<Map<String, String>> dataRoute = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getRouteId() != null)
						unit.put("RouteId", String.valueOf(r.getRouteId()));
					else
						unit.put("RouteId", "");
					if (r.getStartStation() != null)
						unit.put("StartStation", String.valueOf(r.getStartStation()));
					else
						unit.put("StartStation", "");
					if (r.getEndStation() != null)
						unit.put("EndStation", String.valueOf(r.getEndStation()));
					else
						unit.put("EndStation", "");
					if (r.getTime() != null)
						unit.put("Time", String.valueOf(r.getTime()));
					else
						unit.put("Time", "");
					if (r.getTrainId() != null)
						unit.put("TrainId", String.valueOf(r.getTrainId()));
					else
						unit.put("TrainId", "");
					dataRoute.add(unit);
				
				
				tableRoute.setItems(dataRoute);
				operation_return_pane.setContent(tableRoute);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyRoute() {
		
		System.out.println("execute modifyRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyRoute in service: ManageRouteCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageroutecrudservice_service.modifyRoute(
			modifyRoute_routeid_t.getText(),
			modifyRoute_startstation_t.getText(),
			modifyRoute_endstation_t.getText(),
			modifyRoute_time_t.getText(),
			modifyRoute_trainid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteRoute() {
		
		System.out.println("execute deleteRoute");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteRoute in service: ManageRouteCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageroutecrudservice_service.deleteRoute(
			deleteRoute_routeid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createTrain() {
		
		System.out.println("execute createTrain");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createTrain in service: ManageTrainCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managetraincrudservice_service.createTrain(
			createTrain_trainid_t.getText(),
			createTrain_name_t.getText(),
			TrainType.valueOf(createTrain_traintype_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryTrain() {
		
		System.out.println("execute queryTrain");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryTrain in service: ManageTrainCRUDService ");
		
		try {
			//invoke op with parameters
				Train r = managetraincrudservice_service.queryTrain(
				queryTrain_trainid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableTrain = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableTrain_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
				tableTrain_TrainId.setMinWidth("TrainId".length()*10);
				tableTrain_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
				    }
				});	
				tableTrain.getColumns().add(tableTrain_TrainId);
				TableColumn<Map<String, String>, String> tableTrain_Name = new TableColumn<Map<String, String>, String>("Name");
				tableTrain_Name.setMinWidth("Name".length()*10);
				tableTrain_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableTrain.getColumns().add(tableTrain_Name);
				TableColumn<Map<String, String>, String> tableTrain_TrainType = new TableColumn<Map<String, String>, String>("TrainType");
				tableTrain_TrainType.setMinWidth("TrainType".length()*10);
				tableTrain_TrainType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TrainType"));
				    }
				});	
				tableTrain.getColumns().add(tableTrain_TrainType);
				
				ObservableList<Map<String, String>> dataTrain = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getTrainId() != null)
						unit.put("TrainId", String.valueOf(r.getTrainId()));
					else
						unit.put("TrainId", "");
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					unit.put("TrainType", String.valueOf(r.getTrainType()));
					dataTrain.add(unit);
				
				
				tableTrain.setItems(dataTrain);
				operation_return_pane.setContent(tableTrain);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyTrain() {
		
		System.out.println("execute modifyTrain");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyTrain in service: ManageTrainCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managetraincrudservice_service.modifyTrain(
			modifyTrain_trainid_t.getText(),
			modifyTrain_name_t.getText(),
			TrainType.valueOf(modifyTrain_traintype_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteTrain() {
		
		System.out.println("execute deleteTrain");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteTrain in service: ManageTrainCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managetraincrudservice_service.deleteTrain(
			deleteTrain_trainid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createTicket() {
		
		System.out.println("execute createTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createTicket in service: ManageTicketCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageticketcrudservice_service.createTicket(
			createTicket_ticketid_t.getText(),
			createTicket_routeid_t.getText(),
			createTicket_seatid_t.getText(),
			Float.valueOf(createTicket_price_t.getText()),
			Boolean.valueOf(createTicket_isvalid_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryTicket() {
		
		System.out.println("execute queryTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryTicket in service: ManageTicketCRUDService ");
		
		try {
			//invoke op with parameters
				Ticket r = manageticketcrudservice_service.queryTicket(
				queryTicket_ticketid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableTicket = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableTicket_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
				tableTicket_TicketId.setMinWidth("TicketId".length()*10);
				tableTicket_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
				    }
				});	
				tableTicket.getColumns().add(tableTicket_TicketId);
				TableColumn<Map<String, String>, String> tableTicket_RouteId = new TableColumn<Map<String, String>, String>("RouteId");
				tableTicket_RouteId.setMinWidth("RouteId".length()*10);
				tableTicket_RouteId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("RouteId"));
				    }
				});	
				tableTicket.getColumns().add(tableTicket_RouteId);
				TableColumn<Map<String, String>, String> tableTicket_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
				tableTicket_SeatId.setMinWidth("SeatId".length()*10);
				tableTicket_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
				    }
				});	
				tableTicket.getColumns().add(tableTicket_SeatId);
				TableColumn<Map<String, String>, String> tableTicket_Price = new TableColumn<Map<String, String>, String>("Price");
				tableTicket_Price.setMinWidth("Price".length()*10);
				tableTicket_Price.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Price"));
				    }
				});	
				tableTicket.getColumns().add(tableTicket_Price);
				TableColumn<Map<String, String>, String> tableTicket_IsValid = new TableColumn<Map<String, String>, String>("IsValid");
				tableTicket_IsValid.setMinWidth("IsValid".length()*10);
				tableTicket_IsValid.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("IsValid"));
				    }
				});	
				tableTicket.getColumns().add(tableTicket_IsValid);
				
				ObservableList<Map<String, String>> dataTicket = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getTicketId() != null)
						unit.put("TicketId", String.valueOf(r.getTicketId()));
					else
						unit.put("TicketId", "");
					if (r.getRouteId() != null)
						unit.put("RouteId", String.valueOf(r.getRouteId()));
					else
						unit.put("RouteId", "");
					if (r.getSeatId() != null)
						unit.put("SeatId", String.valueOf(r.getSeatId()));
					else
						unit.put("SeatId", "");
					unit.put("Price", String.valueOf(r.getPrice()));
					unit.put("IsValid", String.valueOf(r.getIsValid()));
					dataTicket.add(unit);
				
				
				tableTicket.setItems(dataTicket);
				operation_return_pane.setContent(tableTicket);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyTicket() {
		
		System.out.println("execute modifyTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyTicket in service: ManageTicketCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageticketcrudservice_service.modifyTicket(
			modifyTicket_ticketid_t.getText(),
			modifyTicket_routeid_t.getText(),
			modifyTicket_seatid_t.getText(),
			Float.valueOf(modifyTicket_price_t.getText()),
			Boolean.valueOf(modifyTicket_isvalid_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteTicket() {
		
		System.out.println("execute deleteTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteTicket in service: ManageTicketCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageticketcrudservice_service.deleteTicket(
			deleteTicket_ticketid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createOrder() {
		
		System.out.println("execute createOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.createOrder(
			createOrder_orderid_t.getText(),
			createOrder_ticketid_t.getText(),
			createOrder_accoutid_t.getText(),
			createOrder_createtime_t.getText(),
			OrderStatus.valueOf(createOrder_orderstatus_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryOrder() {
		
		System.out.println("execute queryOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
				Order r = manageordercrudservice_service.queryOrder(
				queryOrder_orderid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableOrder_OrderId = new TableColumn<Map<String, String>, String>("OrderId");
				tableOrder_OrderId.setMinWidth("OrderId".length()*10);
				tableOrder_OrderId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("OrderId"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_OrderId);
				TableColumn<Map<String, String>, String> tableOrder_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
				tableOrder_TicketId.setMinWidth("TicketId".length()*10);
				tableOrder_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_TicketId);
				TableColumn<Map<String, String>, String> tableOrder_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
				tableOrder_AccoutId.setMinWidth("AccoutId".length()*10);
				tableOrder_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_AccoutId);
				TableColumn<Map<String, String>, String> tableOrder_CreateTime = new TableColumn<Map<String, String>, String>("CreateTime");
				tableOrder_CreateTime.setMinWidth("CreateTime".length()*10);
				tableOrder_CreateTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("CreateTime"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_CreateTime);
				TableColumn<Map<String, String>, String> tableOrder_OrderStatus = new TableColumn<Map<String, String>, String>("OrderStatus");
				tableOrder_OrderStatus.setMinWidth("OrderStatus".length()*10);
				tableOrder_OrderStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("OrderStatus"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_OrderStatus);
				
				ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getOrderId() != null)
						unit.put("OrderId", String.valueOf(r.getOrderId()));
					else
						unit.put("OrderId", "");
					if (r.getTicketId() != null)
						unit.put("TicketId", String.valueOf(r.getTicketId()));
					else
						unit.put("TicketId", "");
					if (r.getAccoutId() != null)
						unit.put("AccoutId", String.valueOf(r.getAccoutId()));
					else
						unit.put("AccoutId", "");
					if (r.getCreateTime() != null)
						unit.put("CreateTime", String.valueOf(r.getCreateTime()));
					else
						unit.put("CreateTime", "");
					unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));
					dataOrder.add(unit);
				
				
				tableOrder.setItems(dataOrder);
				operation_return_pane.setContent(tableOrder);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyOrder() {
		
		System.out.println("execute modifyOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.modifyOrder(
			modifyOrder_orderid_t.getText(),
			modifyOrder_ticketid_t.getText(),
			modifyOrder_accoutid_t.getText(),
			modifyOrder_createtime_t.getText(),
			OrderStatus.valueOf(modifyOrder_orderstatus_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteOrder() {
		
		System.out.println("execute deleteOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.deleteOrder(
			deleteOrder_orderid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createAccout() {
		
		System.out.println("execute createAccout");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createAccout in service: ManageAccoutCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageaccoutcrudservice_service.createAccout(
			createAccout_accoutid_t.getText(),
			createAccout_name_t.getText(),
			createAccout_phonenumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryAccout() {
		
		System.out.println("execute queryAccout");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryAccout in service: ManageAccoutCRUDService ");
		
		try {
			//invoke op with parameters
				Accout r = manageaccoutcrudservice_service.queryAccout(
				queryAccout_accoutid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableAccout = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableAccout_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
				tableAccout_AccoutId.setMinWidth("AccoutId".length()*10);
				tableAccout_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
				    }
				});	
				tableAccout.getColumns().add(tableAccout_AccoutId);
				TableColumn<Map<String, String>, String> tableAccout_Name = new TableColumn<Map<String, String>, String>("Name");
				tableAccout_Name.setMinWidth("Name".length()*10);
				tableAccout_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableAccout.getColumns().add(tableAccout_Name);
				TableColumn<Map<String, String>, String> tableAccout_PhoneNumber = new TableColumn<Map<String, String>, String>("PhoneNumber");
				tableAccout_PhoneNumber.setMinWidth("PhoneNumber".length()*10);
				tableAccout_PhoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("PhoneNumber"));
				    }
				});	
				tableAccout.getColumns().add(tableAccout_PhoneNumber);
				
				ObservableList<Map<String, String>> dataAccout = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getAccoutId() != null)
						unit.put("AccoutId", String.valueOf(r.getAccoutId()));
					else
						unit.put("AccoutId", "");
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					if (r.getPhoneNumber() != null)
						unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
					else
						unit.put("PhoneNumber", "");
					dataAccout.add(unit);
				
				
				tableAccout.setItems(dataAccout);
				operation_return_pane.setContent(tableAccout);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyAccout() {
		
		System.out.println("execute modifyAccout");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyAccout in service: ManageAccoutCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageaccoutcrudservice_service.modifyAccout(
			modifyAccout_accoutid_t.getText(),
			modifyAccout_name_t.getText(),
			modifyAccout_phonenumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteAccout() {
		
		System.out.println("execute deleteAccout");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteAccout in service: ManageAccoutCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageaccoutcrudservice_service.deleteAccout(
			deleteAccout_accoutid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createSeat() {
		
		System.out.println("execute createSeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createSeat in service: ManageSeatCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageseatcrudservice_service.createSeat(
			createSeat_seatid_t.getText(),
			createSeat_trainid_t.getText(),
			SeatType.valueOf(createSeat_seattype_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void querySeat() {
		
		System.out.println("execute querySeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: querySeat in service: ManageSeatCRUDService ");
		
		try {
			//invoke op with parameters
				Seat r = manageseatcrudservice_service.querySeat(
				querySeat_seatid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableSeat = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableSeat_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
				tableSeat_SeatId.setMinWidth("SeatId".length()*10);
				tableSeat_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
				    }
				});	
				tableSeat.getColumns().add(tableSeat_SeatId);
				TableColumn<Map<String, String>, String> tableSeat_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
				tableSeat_TrainId.setMinWidth("TrainId".length()*10);
				tableSeat_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
				    }
				});	
				tableSeat.getColumns().add(tableSeat_TrainId);
				TableColumn<Map<String, String>, String> tableSeat_SeatType = new TableColumn<Map<String, String>, String>("SeatType");
				tableSeat_SeatType.setMinWidth("SeatType".length()*10);
				tableSeat_SeatType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("SeatType"));
				    }
				});	
				tableSeat.getColumns().add(tableSeat_SeatType);
				
				ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getSeatId() != null)
						unit.put("SeatId", String.valueOf(r.getSeatId()));
					else
						unit.put("SeatId", "");
					if (r.getTrainId() != null)
						unit.put("TrainId", String.valueOf(r.getTrainId()));
					else
						unit.put("TrainId", "");
					unit.put("SeatType", String.valueOf(r.getSeatType()));
					dataSeat.add(unit);
				
				
				tableSeat.setItems(dataSeat);
				operation_return_pane.setContent(tableSeat);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifySeat() {
		
		System.out.println("execute modifySeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifySeat in service: ManageSeatCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageseatcrudservice_service.modifySeat(
			modifySeat_seatid_t.getText(),
			modifySeat_trainid_t.getText(),
			SeatType.valueOf(modifySeat_seattype_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteSeat() {
		
		System.out.println("execute deleteSeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteSeat in service: ManageSeatCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageseatcrudservice_service.deleteSeat(
			deleteSeat_seatid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void buyTicket() {
		
		System.out.println("execute buyTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: buyTicket in service: BuyTicketService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(buyticketservice_service.buyTicket(
			buyTicket_accoutId_t.getText(),
			buyTicket_routeId_t.getText(),
			buyTicket_ticketId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void saveOrder() {
		
		System.out.println("execute saveOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: saveOrder in service: BuyTicketService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(buyticketservice_service.saveOrder(
			saveOrder_orderId_t.getText(),
			saveOrder_boughtTime_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void showSeats() {
		
		System.out.println("execute showSeats");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: showSeats in service: BuyTicketService ");
		
		try {
			//invoke op with parameters
					List<Seat> result = buyticketservice_service.showSeats(
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableSeat = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableSeat_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
					tableSeat_SeatId.setMinWidth("SeatId".length()*10);
					tableSeat_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_SeatId);
					TableColumn<Map<String, String>, String> tableSeat_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
					tableSeat_TrainId.setMinWidth("TrainId".length()*10);
					tableSeat_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_TrainId);
					TableColumn<Map<String, String>, String> tableSeat_SeatType = new TableColumn<Map<String, String>, String>("SeatType");
					tableSeat_SeatType.setMinWidth("SeatType".length()*10);
					tableSeat_SeatType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("SeatType"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_SeatType);
					
					ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
					for (Seat r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getSeatId() != null)
							unit.put("SeatId", String.valueOf(r.getSeatId()));
						else
							unit.put("SeatId", "");
						if (r.getTrainId() != null)
							unit.put("TrainId", String.valueOf(r.getTrainId()));
						else
							unit.put("TrainId", "");
						unit.put("SeatType", String.valueOf(r.getSeatType()));
						dataSeat.add(unit);
					}
					
					tableSeat.setItems(dataSeat);
					operation_return_pane.setContent(tableSeat);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void selectSeat() {
		
		System.out.println("execute selectSeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: selectSeat in service: BuyTicketService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(buyticketservice_service.selectSeat(
			selectSeat_seatId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void pay() {
		
		System.out.println("execute pay");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: pay in service: BuyTicketService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(buyticketservice_service.pay(
			Float.valueOf(pay_price_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryInformation() {
		
		System.out.println("execute queryInformation");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryInformation in service: TrainTicketSystem ");
		
		try {
			//invoke op with parameters
					List<Route> result = trainticketsystem_service.queryInformation(
					queryInformation_startStation_t.getText(),
					queryInformation_endStation_t.getText(),
					queryInformation_time_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableRoute = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableRoute_RouteId = new TableColumn<Map<String, String>, String>("RouteId");
					tableRoute_RouteId.setMinWidth("RouteId".length()*10);
					tableRoute_RouteId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RouteId"));
					    }
					});	
					tableRoute.getColumns().add(tableRoute_RouteId);
					TableColumn<Map<String, String>, String> tableRoute_StartStation = new TableColumn<Map<String, String>, String>("StartStation");
					tableRoute_StartStation.setMinWidth("StartStation".length()*10);
					tableRoute_StartStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("StartStation"));
					    }
					});	
					tableRoute.getColumns().add(tableRoute_StartStation);
					TableColumn<Map<String, String>, String> tableRoute_EndStation = new TableColumn<Map<String, String>, String>("EndStation");
					tableRoute_EndStation.setMinWidth("EndStation".length()*10);
					tableRoute_EndStation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("EndStation"));
					    }
					});	
					tableRoute.getColumns().add(tableRoute_EndStation);
					TableColumn<Map<String, String>, String> tableRoute_Time = new TableColumn<Map<String, String>, String>("Time");
					tableRoute_Time.setMinWidth("Time".length()*10);
					tableRoute_Time.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Time"));
					    }
					});	
					tableRoute.getColumns().add(tableRoute_Time);
					TableColumn<Map<String, String>, String> tableRoute_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
					tableRoute_TrainId.setMinWidth("TrainId".length()*10);
					tableRoute_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
					    }
					});	
					tableRoute.getColumns().add(tableRoute_TrainId);
					
					ObservableList<Map<String, String>> dataRoute = FXCollections.observableArrayList();
					for (Route r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getRouteId() != null)
							unit.put("RouteId", String.valueOf(r.getRouteId()));
						else
							unit.put("RouteId", "");
						if (r.getStartStation() != null)
							unit.put("StartStation", String.valueOf(r.getStartStation()));
						else
							unit.put("StartStation", "");
						if (r.getEndStation() != null)
							unit.put("EndStation", String.valueOf(r.getEndStation()));
						else
							unit.put("EndStation", "");
						if (r.getTime() != null)
							unit.put("Time", String.valueOf(r.getTime()));
						else
							unit.put("Time", "");
						if (r.getTrainId() != null)
							unit.put("TrainId", String.valueOf(r.getTrainId()));
						else
							unit.put("TrainId", "");
						dataRoute.add(unit);
					}
					
					tableRoute.setItems(dataRoute);
					operation_return_pane.setContent(tableRoute);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void cancelTrip() {
		
		System.out.println("execute cancelTrip");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: cancelTrip in service: TrainTicketSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(trainticketsystem_service.cancelTrip(
			cancelTrip_ticketId_t.getText(),
			cancelTrip_accoutId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryByAccoutId() {
		
		System.out.println("execute queryByAccoutId");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryByAccoutId in service: QueryOrderService ");
		
		try {
			//invoke op with parameters
					List<Order> result = queryorderservice_service.queryByAccoutId(
					queryByAccoutId_accoutId_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableOrder_OrderId = new TableColumn<Map<String, String>, String>("OrderId");
					tableOrder_OrderId.setMinWidth("OrderId".length()*10);
					tableOrder_OrderId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OrderId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_OrderId);
					TableColumn<Map<String, String>, String> tableOrder_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
					tableOrder_TicketId.setMinWidth("TicketId".length()*10);
					tableOrder_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_TicketId);
					TableColumn<Map<String, String>, String> tableOrder_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
					tableOrder_AccoutId.setMinWidth("AccoutId".length()*10);
					tableOrder_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_AccoutId);
					TableColumn<Map<String, String>, String> tableOrder_CreateTime = new TableColumn<Map<String, String>, String>("CreateTime");
					tableOrder_CreateTime.setMinWidth("CreateTime".length()*10);
					tableOrder_CreateTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CreateTime"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_CreateTime);
					TableColumn<Map<String, String>, String> tableOrder_OrderStatus = new TableColumn<Map<String, String>, String>("OrderStatus");
					tableOrder_OrderStatus.setMinWidth("OrderStatus".length()*10);
					tableOrder_OrderStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OrderStatus"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_OrderStatus);
					
					ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
					for (Order r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getOrderId() != null)
							unit.put("OrderId", String.valueOf(r.getOrderId()));
						else
							unit.put("OrderId", "");
						if (r.getTicketId() != null)
							unit.put("TicketId", String.valueOf(r.getTicketId()));
						else
							unit.put("TicketId", "");
						if (r.getAccoutId() != null)
							unit.put("AccoutId", String.valueOf(r.getAccoutId()));
						else
							unit.put("AccoutId", "");
						if (r.getCreateTime() != null)
							unit.put("CreateTime", String.valueOf(r.getCreateTime()));
						else
							unit.put("CreateTime", "");
						unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));
						dataOrder.add(unit);
					}
					
					tableOrder.setItems(dataOrder);
					operation_return_pane.setContent(tableOrder);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryTicketId() {
		
		System.out.println("execute queryTicketId");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryTicketId in service: QueryOrderService ");
		
		try {
			//invoke op with parameters
					List<Order> result = queryorderservice_service.queryTicketId(
					queryTicketId_ticketId_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableOrder_OrderId = new TableColumn<Map<String, String>, String>("OrderId");
					tableOrder_OrderId.setMinWidth("OrderId".length()*10);
					tableOrder_OrderId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OrderId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_OrderId);
					TableColumn<Map<String, String>, String> tableOrder_TicketId = new TableColumn<Map<String, String>, String>("TicketId");
					tableOrder_TicketId.setMinWidth("TicketId".length()*10);
					tableOrder_TicketId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("TicketId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_TicketId);
					TableColumn<Map<String, String>, String> tableOrder_AccoutId = new TableColumn<Map<String, String>, String>("AccoutId");
					tableOrder_AccoutId.setMinWidth("AccoutId".length()*10);
					tableOrder_AccoutId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("AccoutId"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_AccoutId);
					TableColumn<Map<String, String>, String> tableOrder_CreateTime = new TableColumn<Map<String, String>, String>("CreateTime");
					tableOrder_CreateTime.setMinWidth("CreateTime".length()*10);
					tableOrder_CreateTime.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CreateTime"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_CreateTime);
					TableColumn<Map<String, String>, String> tableOrder_OrderStatus = new TableColumn<Map<String, String>, String>("OrderStatus");
					tableOrder_OrderStatus.setMinWidth("OrderStatus".length()*10);
					tableOrder_OrderStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OrderStatus"));
					    }
					});	
					tableOrder.getColumns().add(tableOrder_OrderStatus);
					
					ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
					for (Order r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getOrderId() != null)
							unit.put("OrderId", String.valueOf(r.getOrderId()));
						else
							unit.put("OrderId", "");
						if (r.getTicketId() != null)
							unit.put("TicketId", String.valueOf(r.getTicketId()));
						else
							unit.put("TicketId", "");
						if (r.getAccoutId() != null)
							unit.put("AccoutId", String.valueOf(r.getAccoutId()));
						else
							unit.put("AccoutId", "");
						if (r.getCreateTime() != null)
							unit.put("CreateTime", String.valueOf(r.getCreateTime()));
						else
							unit.put("CreateTime", "");
						unit.put("OrderStatus", String.valueOf(r.getOrderStatus()));
						dataOrder.add(unit);
					}
					
					tableOrder.setItems(dataOrder);
					operation_return_pane.setContent(tableOrder);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void updateTicket() {
		
		System.out.println("execute updateTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: updateTicket in service: ModifyTripService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(modifytripservice_service.updateTicket(
			updateTicket_accoutId_t.getText(),
			updateTicket_ticketId_t.getText(),
			updateTicket_newRouteId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void updateOrder() {
		
		System.out.println("execute updateOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: updateOrder in service: ModifyTripService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(modifytripservice_service.updateOrder(
			updateOrder_time_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void showSeatsByRouteId() {
		
		System.out.println("execute showSeatsByRouteId");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: showSeatsByRouteId in service: ModifyTripService ");
		
		try {
			//invoke op with parameters
					List<Seat> result = modifytripservice_service.showSeatsByRouteId(
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableSeat = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableSeat_SeatId = new TableColumn<Map<String, String>, String>("SeatId");
					tableSeat_SeatId.setMinWidth("SeatId".length()*10);
					tableSeat_SeatId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("SeatId"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_SeatId);
					TableColumn<Map<String, String>, String> tableSeat_TrainId = new TableColumn<Map<String, String>, String>("TrainId");
					tableSeat_TrainId.setMinWidth("TrainId".length()*10);
					tableSeat_TrainId.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("TrainId"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_TrainId);
					TableColumn<Map<String, String>, String> tableSeat_SeatType = new TableColumn<Map<String, String>, String>("SeatType");
					tableSeat_SeatType.setMinWidth("SeatType".length()*10);
					tableSeat_SeatType.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("SeatType"));
					    }
					});	
					tableSeat.getColumns().add(tableSeat_SeatType);
					
					ObservableList<Map<String, String>> dataSeat = FXCollections.observableArrayList();
					for (Seat r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getSeatId() != null)
							unit.put("SeatId", String.valueOf(r.getSeatId()));
						else
							unit.put("SeatId", "");
						if (r.getTrainId() != null)
							unit.put("TrainId", String.valueOf(r.getTrainId()));
						else
							unit.put("TrainId", "");
						unit.put("SeatType", String.valueOf(r.getSeatType()));
						dataSeat.add(unit);
					}
					
					tableSeat.setItems(dataSeat);
					operation_return_pane.setContent(tableSeat);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void selectNewSeat() {
		
		System.out.println("execute selectNewSeat");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: selectNewSeat in service: ModifyTripService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(modifytripservice_service.selectNewSeat(
			selectNewSeat_seatId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void payDifference() {
		
		System.out.println("execute payDifference");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: payDifference in service: ModifyTripService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(modifytripservice_service.payDifference(
			Float.valueOf(payDifference_difference_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteOverdueTicket() {
		
		System.out.println("execute deleteOverdueTicket");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteOverdueTicket in service: DeleteOverdueOrderService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(deleteoverdueorderservice_service.deleteOverdueTicket(
			deleteOverdueTicket_ticketId_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteOverdueOrder() {
		
		System.out.println("execute deleteOverdueOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteOverdueOrder in service: DeleteOverdueOrderService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(deleteoverdueorderservice_service.deleteOverdueOrder(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void sendNotification() {
		
		System.out.println("execute sendNotification");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: sendNotification in service: ThirdPartyServices ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(thirdpartyservices_service.sendNotification(
			sendNotification_phoneNumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}




	//select object index
	int objectindex;
	
	@FXML
	TabPane mainPane;

	@FXML
	TextArea log;
	
	@FXML
	TreeView<String> actor_treeview_passenger;
	@FXML
	TreeView<String> actor_treeview_thirdsystem;
	@FXML
	TreeView<String> actor_treeview_staff;
	@FXML
	TreeView<String> actor_treeview_administrator;
	


	@FXML
	TextArea definition;
	@FXML
	TextArea precondition;
	@FXML
	TextArea postcondition;
	@FXML
	TextArea invariants;
	
	@FXML
	TitledPane precondition_pane;
	@FXML
	TitledPane postcondition_pane;
	
	//chosen operation textfields
	List<TextField> choosenOperation;
	String clickedOp;
		
	@FXML
	TableView<ClassInfo> class_statisic;
	@FXML
	TableView<AssociationInfo> association_statisic;
	
	Map<String, ObservableList<AssociationInfo>> allassociationData;
	ObservableList<ClassInfo> classInfodata;
	
	TrainTicketSystem trainticketsystem_service;
	ThirdPartyServices thirdpartyservices_service;
	BuyTicketService buyticketservice_service;
	QueryOrderService queryorderservice_service;
	ManageRouteCRUDService manageroutecrudservice_service;
	ManageTrainCRUDService managetraincrudservice_service;
	ManageTicketCRUDService manageticketcrudservice_service;
	ManageOrderCRUDService manageordercrudservice_service;
	ManageAccoutCRUDService manageaccoutcrudservice_service;
	ManageSeatCRUDService manageseatcrudservice_service;
	ModifyTripService modifytripservice_service;
	DeleteOverdueOrderService deleteoverdueorderservice_service;
	
	ClassInfo route;
	ClassInfo train;
	ClassInfo ticket;
	ClassInfo order;
	ClassInfo accout;
	ClassInfo seat;
		
	@FXML
	TitledPane object_statics;
	Map<String, TableView> allObjectTables;
	
	@FXML
	TitledPane operation_paras;
	
	@FXML
	TitledPane operation_return_pane;
	
	@FXML 
	TitledPane all_invariant_pane;
	
	@FXML
	TitledPane invariants_panes;
	
	Map<String, GridPane> operationPanels;
	Map<String, VBox> opInvariantPanel;
	
	//all textfiled or eumntity
	TextField createRoute_routeid_t;
	TextField createRoute_startstation_t;
	TextField createRoute_endstation_t;
	TextField createRoute_time_t;
	TextField createRoute_trainid_t;
	TextField queryRoute_routeid_t;
	TextField modifyRoute_routeid_t;
	TextField modifyRoute_startstation_t;
	TextField modifyRoute_endstation_t;
	TextField modifyRoute_time_t;
	TextField modifyRoute_trainid_t;
	TextField deleteRoute_routeid_t;
	TextField createTrain_trainid_t;
	TextField createTrain_name_t;
	ChoiceBox createTrain_traintype_cb;
	TextField queryTrain_trainid_t;
	TextField modifyTrain_trainid_t;
	TextField modifyTrain_name_t;
	ChoiceBox modifyTrain_traintype_cb;
	TextField deleteTrain_trainid_t;
	TextField createTicket_ticketid_t;
	TextField createTicket_routeid_t;
	TextField createTicket_seatid_t;
	TextField createTicket_price_t;
	TextField createTicket_isvalid_t;
	TextField queryTicket_ticketid_t;
	TextField modifyTicket_ticketid_t;
	TextField modifyTicket_routeid_t;
	TextField modifyTicket_seatid_t;
	TextField modifyTicket_price_t;
	TextField modifyTicket_isvalid_t;
	TextField deleteTicket_ticketid_t;
	TextField createOrder_orderid_t;
	TextField createOrder_ticketid_t;
	TextField createOrder_accoutid_t;
	TextField createOrder_createtime_t;
	ChoiceBox createOrder_orderstatus_cb;
	TextField queryOrder_orderid_t;
	TextField modifyOrder_orderid_t;
	TextField modifyOrder_ticketid_t;
	TextField modifyOrder_accoutid_t;
	TextField modifyOrder_createtime_t;
	ChoiceBox modifyOrder_orderstatus_cb;
	TextField deleteOrder_orderid_t;
	TextField createAccout_accoutid_t;
	TextField createAccout_name_t;
	TextField createAccout_phonenumber_t;
	TextField queryAccout_accoutid_t;
	TextField modifyAccout_accoutid_t;
	TextField modifyAccout_name_t;
	TextField modifyAccout_phonenumber_t;
	TextField deleteAccout_accoutid_t;
	TextField createSeat_seatid_t;
	TextField createSeat_trainid_t;
	ChoiceBox createSeat_seattype_cb;
	TextField querySeat_seatid_t;
	TextField modifySeat_seatid_t;
	TextField modifySeat_trainid_t;
	ChoiceBox modifySeat_seattype_cb;
	TextField deleteSeat_seatid_t;
	TextField buyTicket_accoutId_t;
	TextField buyTicket_routeId_t;
	TextField buyTicket_ticketId_t;
	TextField saveOrder_orderId_t;
	TextField saveOrder_boughtTime_t;
	TextField selectSeat_seatId_t;
	TextField pay_price_t;
	TextField queryInformation_startStation_t;
	TextField queryInformation_endStation_t;
	TextField queryInformation_time_t;
	TextField cancelTrip_ticketId_t;
	TextField cancelTrip_accoutId_t;
	TextField queryByAccoutId_accoutId_t;
	TextField queryTicketId_ticketId_t;
	TextField updateTicket_accoutId_t;
	TextField updateTicket_ticketId_t;
	TextField updateTicket_newRouteId_t;
	TextField updateOrder_time_t;
	TextField selectNewSeat_seatId_t;
	TextField payDifference_difference_t;
	TextField deleteOverdueTicket_ticketId_t;
	TextField sendNotification_phoneNumber_t;
	
	HashMap<String, String> definitions_map;
	HashMap<String, String> preconditions_map;
	HashMap<String, String> postconditions_map;
	HashMap<String, String> invariants_map;
	LinkedHashMap<String, String> service_invariants_map;
	LinkedHashMap<String, String> entity_invariants_map;
	LinkedHashMap<String, Label> service_invariants_label_map;
	LinkedHashMap<String, Label> entity_invariants_label_map;
	LinkedHashMap<String, Label> op_entity_invariants_label_map;
	LinkedHashMap<String, Label> op_service_invariants_label_map;
	

	
}
