package com;

/*import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;*/
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
// 07-10-24GPC
//08-10-24DCTTarget
public class S2Y{
//private static Logger logger = LogFactory.getLog(STEPXMLCode.class);
//private static Log logger = LogFactory.getLog(STEPXMLCode.class);
	static class ApplicableMarket{
		String code;
		String country;
		String age;
		String date;
		ApplicableMarket(String code,String country,String age,String date){
			this.code=code;
			this.country=country;
			this.age=age;
			this.date=date;
		}
		
	}
	
	private static void processCountrySites(Map<String,String>  map,String country, Map<String,List<ApplicableMarket>> codeMap){
		Pattern p = Pattern.compile("sitesExpe#(\\d+)/(code|ageMaxiDepart|contratDate)");
		Map<String, Map<String,String>> siteIndexMap = new HashMap<>();
		for(Entry<String, String> entry1 : map.entrySet()){
			Matcher m=p.matcher(entry1.getKey());
			if(m.matches()){
				String index=m.group(1);
				String field=m.group(2);		
				String value=entry1.getValue();			
				siteIndexMap.computeIfAbsent(index, K-> new HashMap<>()).put(field, value);
			}
		}		
		for(Map<String,String> siteInfo : siteIndexMap.values()){
			String siteCode= siteInfo.get("code");		
			String ageMaxiDepart= siteInfo.get("ageMaxiDepart");		
			String contratDate= siteInfo.get("contratDate");		
			if(siteCode !=null){
				codeMap.computeIfAbsent(siteCode, K-> new ArrayList<>()).add(new ApplicableMarket(siteCode,country,ageMaxiDepart,contratDate));
			}
		}
		
	}
	private static int comparePriority(String s1,String s2){
		List<String> order = Arrays.asList("GDSStatus","statusERP","statusGTIN");
		return Integer.compare(order.indexOf(s1), order.indexOf(s2));		
	}
	private static int ulcomparePriority(String s1,String s2){
		List<String> order = Arrays.asList("statusERP","statusAPO","statusGTIN");
		return Integer.compare(order.indexOf(s1), order.indexOf(s2));		
	}
	public static void main(String[] args) throws Exception {
		
		String outputFilePath = "C:\\savencia\\Output\\issue1.xml";
//		String outputFilePath = "C:\\savencia\\Output\\ArchiveRecipeMissingTestFile.xml";
//		String outputFilePath = "C:\\savencia\\Output\\ArchivePackageMissing.xml";
//		String outputFilePath = "C:\\savencia\\Output\\ArchiveCheck.xml";
//		String outputFilePath = "C:\\savencia\\Output\\FabGap.xml";
//		String input = "C:\\savencia\\Input\\jul04testingfile.txt";
//		String input = "C:\\savencia\\Input\\ArchiveRecipeMissingTestFile.txt";
//		String input = "C:\\savencia\\Input\\ArchivePackageMissing.txt";
//		String input = "C:\\savencia\\Input\\ArchiveTestFile.txt";
//		String input = "C:\\savencia\\Input\\ArchiveCheck.txt";
//	     String input = "C:\\savencia\\Input\\Consolidated_Catalog_Products_38PROD.txt";
	     String input = "C:\\savencia\\Input\\issue1.txt";
//	     String input = "C:\\savencia\\Input\\secondchoice.txt";
//	     String input = "C:\\savencia\\Input\\polyTestingfile.txt";
//		String input = "C:\\savencia\\Input\\jul04testing2.txt";
      String Config ="C:\\savencia\\Config\\Mapping.txt";
//      String Qualifer = "C:\\savencia\\Config\\Latest_Qualifier_XML_0312.xml";
      String Qualifer = "C:\\savencia\\Config\\Qualifier 11.xml";
      String propertieFile = "C:\\savencia\\Config\\config.properties";
      String sitevalue = "C:\\savencia\\Config\\SiteKey.properties";
      String contextvalue = "C:\\savencia\\Config\\Context.properties";
		Document doc = createXMLDocument(Qualifer,input,Config,propertieFile,sitevalue,contextvalue);
		writeXMLToFile(doc, outputFilePath);		
	 }
	
	
	public static Element getReplacementRules(Document doc) {
        Element rules = doc.createElement("ReplacementRules");
        Element rulesElement = doc.createElement("Products");
        
        Element rulesValue = doc.createElement("ReplaceDataContainers");
        Element physicosValue = doc.createElement("ReplaceDataContainers");
        Element additionValue = doc.createElement("ReplaceDataContainers");
        Element packValue = doc.createElement("ReplaceDataContainers");
        Element CompackValue = doc.createElement("ReplaceDataContainers");
        // Element media = doc.createElement("ReplaceDataContainers");
        
        Element TradeitemPack = doc.createElement("ReplaceCrossReferences");
        Element TradeItemReference_CA_DS_PL_MM = doc.createElement("ReplaceCrossReferences");
        Element variantRecipe = doc.createElement("ReplaceCrossReferences");
        Element variantPack = doc.createElement("ReplaceCrossReferences");
        Element PackUC = doc.createElement("ReplaceCrossReferences");
        Element Promotion = doc.createElement("ReplaceCrossReferences");
        Element FirstChoiceProduct = doc.createElement("ReplaceCrossReferences");
        Element TradeItemReferenceTL = doc.createElement("ReplaceCrossReferences");
        Element ServingSizeNutrients = doc.createElement("ReplaceCrossReferences");
        Element RecipeServingSize = doc.createElement("ReplaceCrossReferences");
        Element RecipeAllergen = doc.createElement("ReplaceCrossReferences");
        Element NonNutrientClaims = doc.createElement("ReplaceCrossReferences");
        Element CustomClassificationType = doc.createElement("ReplaceCrossReferences");
        Element Temperature = doc.createElement("ReplaceCrossReferences");
        // Element AdditionalRegulatoryClassification = doc.createElement("ReplaceCrossReferences");
        Element VariantServingSize = doc.createElement("ReplaceCrossReferences");
        Element AddTradeItemIdentificationType = doc.createElement("ReplaceCrossReferences");
        Element DietTypeInfo = doc.createElement("ReplaceCrossReferences");
        Element AdditionalClassification = doc.createElement("ReplaceCrossReferences");
        
        Element marketLink = doc.createElement("ReplaceClassificationReferences");
        Element siteRole = doc.createElement("ReplaceClassificationReferences");
        Element MacroArticle = doc.createElement("ReplaceClassificationReferences");
        Element ProductGTLink = doc.createElement("ReplaceClassificationReferences");
        Element ProductBrand = doc.createElement("ReplaceClassificationReferences");
        Element ProductGPCBrickLink = doc.createElement("ReplaceClassificationReferences");

        rulesValue.setAttribute("DataContainerTypeID", "Dct_Microbio_Criteria");
        physicosValue.setAttribute("DataContainerTypeID", "Dct_Physico_Chemical_Criteria");
        additionValue.setAttribute("DataContainerTypeID", "Dct_Additional_Microbiological_Criteria");
        packValue.setAttribute("DataContainerTypeID", "Dct_PackagingMaterial");
        CompackValue.setAttribute("DataContainerTypeID", "Dct_Composite_Packaging_Material");
        // media.setAttribute("DataContainerTypeID", "Dct_Media");
        
        TradeitemPack.setAttribute("ReferenceTypeID", "Rpp_TradeItem_Pack");
        TradeItemReference_CA_DS_PL_MM.setAttribute("ReferenceTypeID", "Rpp_TradeItemReference_CA_DS_PL_MM");
        variantRecipe.setAttribute("ReferenceTypeID", "Rpp_Variant_Recipe");
        variantPack.setAttribute("ReferenceTypeID", "Rpp_Variant_Pack");
        PackUC.setAttribute("ReferenceTypeID", "Rpp_Pack_UC");
        Promotion.setAttribute("ReferenceTypeID", "Rpp_Promotion");
        FirstChoiceProduct.setAttribute("ReferenceTypeID", "First_Choice_Product");
        TradeItemReferenceTL.setAttribute("ReferenceTypeID", "Rpp_TradeItemReference_TL");
        ServingSizeNutrients.setAttribute("ReferenceTypeID", "Ree_ServingSize_Nutrients");
        RecipeServingSize.setAttribute("ReferenceTypeID", "Rpe_Recipe_ServingSize");
        RecipeAllergen.setAttribute("ReferenceTypeID", "Rpe_Recipe_Allergen");
        NonNutrientClaims.setAttribute("ReferenceTypeID", "Rpe_NonNutrientClaims");
        CustomClassificationType.setAttribute("ReferenceTypeID", "Rpe_CustomClassificationType");
        Temperature.setAttribute("ReferenceTypeID", "Rpe_Temperature");
        VariantServingSize.setAttribute("ReferenceTypeID", "Rpe_Variant_Serving_Size");
        AddTradeItemIdentificationType.setAttribute("ReferenceTypeID", "Rpe_AddTradeItemIdentificationType");
        DietTypeInfo.setAttribute("ReferenceTypeID", "Rpe_DietType_Info");
        AdditionalClassification.setAttribute("ReferenceTypeID", "Rpe_AdditionalClassification");
        
        marketLink.setAttribute("ReferenceTypeID", "Rpc_Market_Link");
        siteRole.setAttribute("ReferenceTypeID", "Rpc_Site_Role");
        MacroArticle.setAttribute("ReferenceTypeID", "Rpc_Macro_Article");
        ProductGTLink.setAttribute("ReferenceTypeID", "Rpc_Product_GT_Link");
        ProductBrand.setAttribute("ReferenceTypeID", "Rpc_Product_Brand");
        ProductGPCBrickLink.setAttribute("ReferenceTypeID", "Rpc_Product_GPC_Brick_Link");

        rulesElement.appendChild(rulesValue);
        rulesElement.appendChild(physicosValue);
        rulesElement.appendChild(additionValue);
        rulesElement.appendChild(packValue);
        rulesElement.appendChild(CompackValue);
        // rulesElement.appendChild(media);
        
        rulesElement.appendChild(TradeitemPack);
        rulesElement.appendChild(TradeItemReference_CA_DS_PL_MM);
        rulesElement.appendChild(variantRecipe);
        rulesElement.appendChild(variantPack);
        rulesElement.appendChild(PackUC);
        rulesElement.appendChild(Promotion);
        rulesElement.appendChild(FirstChoiceProduct);
        rulesElement.appendChild(TradeItemReferenceTL);
        rulesElement.appendChild(ServingSizeNutrients);
        rulesElement.appendChild(RecipeServingSize);
        rulesElement.appendChild(RecipeAllergen);
        rulesElement.appendChild(NonNutrientClaims);
        rulesElement.appendChild(CustomClassificationType);
        rulesElement.appendChild(Temperature);
        rulesElement.appendChild(VariantServingSize);
        rulesElement.appendChild(AddTradeItemIdentificationType);
        rulesElement.appendChild(DietTypeInfo);
        rulesElement.appendChild(AdditionalClassification);
        
        rulesElement.appendChild(marketLink);
        rulesElement.appendChild(siteRole);
        rulesElement.appendChild(MacroArticle);
        rulesElement.appendChild(ProductGTLink);
        rulesElement.appendChild(ProductBrand);
        rulesElement.appendChild(ProductGPCBrickLink);

        rules.appendChild(rulesElement);		
        
        return rules;
    }
	
	private static Element getVaraintPolyphosphate(Document doc,String lValue,String nNc){
		Element nonNutrientClaims = doc.createElement("EntityCrossReference");
        nonNutrientClaims.setAttribute("EntityID", lValue);
        nonNutrientClaims.setAttribute("Type", "Rpe_NonNutrientClaims");
        Element nNCMetaData = doc.createElement("MetaData");
        Element nNCElt = doc.createElement("Value");
        nNCElt.setAttribute("AttributeID", "Att_Nut_Clm_elt");
        nNCElt.setAttribute("QualifierID", "AllCountries");
        nNCElt.setAttribute("ID", lValue);
        nNCMetaData.appendChild(nNCElt);
        Element nNCValue = doc.createElement("Value");
        nNCValue.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
        nNCValue.setAttribute("QualifierID", "AllCountries");
        nNCValue.setAttribute("ID", nNc);
        nNCMetaData.appendChild(nNCValue);
        nonNutrientClaims.appendChild(nNCMetaData);
		return nonNutrientClaims;	
	}

	
	private static Document createXMLDocument(String Qualifer, String input,String Config,String propertieFile,String sitevalue,String contextvalue) throws Exception {
	//	System.setProperty("log4j.configurationFile", "C:/Uma/XML/Final/Log.properties");
		// Step 1: Read and parse input data
		Map<String, List<String[]>> dataMap = new HashMap<>();
		Map<String, List<Element>> valueElements = new HashMap<>();
		Map<String, Map<String, String>> descriptions = new LinkedHashMap<>();
		Map<String, Map<String, String>> dataM = new HashMap<>();
		Map<String, String> ProdId = new HashMap<>();
		Map<String, List<String[]>> recipeMap = new HashMap<>();
		Map<String, List<String[]>> packagingMap = new HashMap<>();
		Map<String, String> RecipeID = new HashMap<>();
		Map<String, String> SavenciaCaseID = new HashMap<>();
		Map<String, String> isItemPk = new HashMap<>();
		Map<String, String> TradeItemPack = new HashMap<>();
		Map<String, String> VariantPack = new HashMap<>();
		Map<String, String> grade = new HashMap<>();
		List<String[]> att = new ArrayList<>();
		Map<String,Set<String>> SDSmap = new HashMap<>();
		Map<String,String> SCDName = new HashMap<>();		
		Map<String, String> groupTechnology = new HashMap<>();
		Map<String, String> marquePrincipale = new HashMap<>();
		Map<String, String> packagingTypeCode = new HashMap<>();
		Map<String, String> stockOwner = new HashMap<>();
		Map<String, String> pKBUnite = new HashMap<>();
		Map<String, String> pKCUnite = new HashMap<>();
		Map<String, String> SPCode = new HashMap<>();
		Map<String, String> SCRefproduct = new HashMap<>();
	//	Map<String, String> iteamDN = new HashMap<>();
		Map<String, String> SCULMapping = new HashMap<>();
		Map<String, String> servingsize = new HashMap<>();
		Map<String, String> declaredUnity = new HashMap<>();
		Map<String, String> packageValidation = new HashMap<>();
		Map<String, String> receipeValidatiion = new HashMap<>();
		Map<String, String> fatPercode = new HashMap<>();
		String entity = null;
		String category = null;
		String Configpath = null;
		String attrib = null;
		String x = null;
		String baseUnite= null;
		String ConUnite= null;
		String SCRefProd= null;
		String iteamDisplayName=null;
		//String SCTradeitem=null;
		String[] packattributes =null;
		String[] variantattributes =null;
		String[] proudctonly =null;
		String[] tradeItemOnly =null;
		String[] onlyvariantAttributes =null;
		String[] nonNutrient =null;
		Properties siterole = new Properties();	
		Properties contextProp = new Properties();
		String productIDBC=null;
		String typeProduits=null;
		String servingSize=null;
		try{
        	Properties properties = new Properties();
        	FileInputStream fis = new FileInputStream(propertieFile);
        	properties.load(fis);        	
        	FileInputStream site = new FileInputStream(sitevalue);
        	siterole.load(site);
        	FileInputStream context = new FileInputStream(contextvalue);
        	contextProp.load(context);       	
        	
        	String packageValues= properties.getProperty("package").trim();
        	String variantValues= properties.getProperty("variant").trim();        	
        	String onlyvariant= properties.getProperty("onlyvariant").trim();
        	String onlyProduct= properties.getProperty("onlyproudct").trim();        	
        	String nNutrient= properties.getProperty("NonNutrient").trim();        	
        	packattributes= packageValues !=null ? packageValues.split(",") : new String[0];
        	variantattributes= variantValues !=null ? variantValues.split(",") : new String[0];
        	proudctonly= onlyProduct !=null ? onlyProduct.split(",") : new String[0];        
        	onlyvariantAttributes=onlyvariant !=null ? onlyvariant.split(",") : new String[0];
        	nonNutrient = nNutrient !=null ? nNutrient.split(",") : new String[0];        	
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null && !line.isEmpty()) {
				String[] parts = line.split("\\|");
				if (parts.length == 5) {
					String group = parts[0];
					String id = parts[1];
					String attribute = parts[2];
					String pCode = parts[3];
					String value = parts[4];
					
					if(!pCode.equals("targetMarketCountryCode") && (value.equals("BEO") || value.equals("EVI"))){
						value="FR";
					}
					if(pCode.equals("targetMarketCountryCode") && value.equals("PC")  ){
						value="FR";	
					}
					if(pCode.equals("targetMarketCountryCode") && value.equals("ML")  ){
						value="ML_L2";	
					}
				    String attrd = attribute + "_" + pCode;
				    String tradeItemUnitDescriptor=group+"_"+attrd;
					attrd = attrd.replaceAll("#\\d+","");
					if ((attribute.equals("processMicrobiologicalInformation"))
							|| (attribute.equals("microbiologicalInformation"))) {
						String[] indexObj = splitInputData(attrd);
						attrd = indexObj[0];
					}
					if ((!recipeAttributes(attrd)) && (!Arrays.toString(packattributes).equalsIgnoreCase(attrd)) && (!packagingSubType(attrd))) {
						dataMap.computeIfAbsent(id, k -> new ArrayList<>())
								.add(new String[] { group, attribute, pCode, value });
											
					}
					if (recipeAttributes(attrd)) {						
						recipeMap.computeIfAbsent(id+10, k -> new ArrayList<>())
								.add(new String[] { group, attribute, pCode, value });
						ProdId.put(id+10, "REC");
					}

					if (Arrays.toString(packattributes).contains(attrd) || packagingSubType(attrd)) {						
						packagingMap.computeIfAbsent(id+"20", k -> new ArrayList<>())
								.add(new String[] { group, attribute, pCode, value });
						ProdId.put(id+"20", "PACK");
					}
					if (pCode.equals("IsPackagingMarkedWithIngredients")|| attribute.equals("ingredientStatement")|| pCode.replaceAll("#\\d+","").equals("nutritionalClaimNonNutrientElement")||pCode.replaceAll("#\\d+","").equals("nutritionalClaimNutrientElement")|| pCode.replaceAll("#\\d+","").equals("nutritionalClaimType")|| pCode.replaceAll("#\\d+","").equals("VariantnutritionalClaimNutrientElement")|| pCode.replaceAll("#\\d+","").equals("VariantnutritionalClaimType")) {
						dataMap.computeIfAbsent(id, k -> new ArrayList<>())
								.add(new String[] { group, attribute, pCode, value });
											
					}
					if(!tradeItemUnitDescriptor.equals("Fiches_Groupe_UL_tradeItemUnitDescriptor_tradeItemUnitDescriptor")){
	           
					dataM.computeIfAbsent(id, k -> new HashMap<>()).put(pCode, value);
                     }				
					}
			} 
		}
		
		for (Entry<String, Map<String, String>> entry : dataM.entrySet()) {

			String productId = entry.getKey();
			Map<String, String> productDetails = entry.getValue();		
			x = productDetails.getOrDefault("tradeItemUnitDescriptor", "Unknown");					
			ProdId.put(productId, x);			
		}
         
		
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
            String productId = entry.getKey();
            Map<String,String> productDetails = entry.getValue();
             x = productDetails.getOrDefault("lienCarProduit", "Unknown");           
             RecipeID.put(productId+10, x);
             }
         
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
            	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("lienCarProduitPK", "Unknown");           
              RecipeID.put(productId+10, x);
              }
         
         
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
            	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("linkedTradeItem", "Unknown");
              SavenciaCaseID.put(productId, x);
              x = productDetails.getOrDefault("linkedTradeItemPK", "Unknown");
              isItemPk.put(productId, x);
              }
   
                             /*Package key*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
         	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("lienEmballage", "Unknown");            
              TradeItemPack.put(productId, x);
              }
         
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("lienEmballagePK", "Unknown");            
              TradeItemPack.put(productId, x);
              }
         
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
         	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("lienEmballagePK", "Unknown");
              VariantPack.put(productId, x);
              }
         
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
          	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("grade", "Unknown");
              grade.put(productId, x);
              }
         for (Entry<String, List<String[]>> entry : dataMap.entrySet()) {
        	 String productId = entry.getKey();
 		List<String[]> productDetails = entry.getValue();
 		
 		String lValue = null;
 		for (String[] detail : productDetails) {
			String group = detail[0];			
			//String product = detail[1];
			String value = detail[2];
			 lValue = detail[3];
			 
				
			if(value.equals("linkedTradeItem")||value.equals("linkedTradeItemPK")){
				SDSmap.putIfAbsent(productId, new HashSet<>());	
				SDSmap.get(productId).add(lValue);				
			}
			
			if(group.equals("EANUCC_Spec") && value.equals("itemdisplayName")){				
				SCDName.putIfAbsent(productId, lValue);	
				
			}
			}
 		
 		}
         /*------------Recipe parent key groupTechnology--------------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("groupTechnology", "Unknown");
            if(!x.equals("Unknown")){
              groupTechnology.put(productId, x);
              }
              }
         /*------------Recipe parent key marquePrincipale----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("marquePrincipale", "Unknown");            
              if(!x.equals("Unknown")){
              marquePrincipale.put(productId, x);
              }
              }
         /*------------Package parent key packagingTypeCode----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("packagingTypeCode", "Unknown");
               if(!x.equals("Unknown")){
            	  packagingTypeCode.put(productId, x);
              }else{
            	  packagingTypeCode.put(productId, "NON");
              }
              }                 
         /*------------stockOwner----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
              x = productDetails.getOrDefault("Societes - Commercialise", "Unknown");
             if(!x.equals("Unknown")){
            	  stockOwner.put(productId, x);
              }
              }                 

         /*------------Pack Consumer and Base Unit----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             baseUnite = productDetails.getOrDefault("isTradeItemABaseUnit", "Unknown");  
              pKBUnite.put(productId, baseUnite);                
              } 
         
         /*------------Pack Consumer and Base Unit----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             ConUnite = productDetails.getOrDefault("isTradeItemAConsumerUnit", "Unknown");            
             pKCUnite.put(productId, ConUnite);                
         }
         /*------------Second choice and corresponding product mapping----------*/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
           	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             SCRefProd = productDetails.getOrDefault("linkedG1Item", "Unknown");            
             SCRefproduct.put(productId, SCRefProd);                
         }
         /**---------Take SecondChoice display name--------------------**/
         
       /*  for (Map.Entry<String, Map<String,String>> entry : dataM.entrySet()) {
            	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             SCTradeitem = productDetails.getOrDefault("itemdisplayName", "Unknown");            
             iteamDN.put(productId, iteamDisplayName);                
         }*/
         /**---------------- Second Choice Trade item mapping UL--------- **/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
         	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             typeProduits = productDetails.getOrDefault("Types Produits", "Unknown");            
             SCULMapping.put(productId, typeProduits);                
         }
         /**---------------- Serving Size value --------- **/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
         	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             servingSize = productDetails.getOrDefault("servingSize", "NO");        
             if(!servingSize.equals("NO")){         
             servingsize.put(productId, servingSize);  
             }       
         }
         
         for (Entry<String, Map<String, String>> entry : dataM.entrySet()) {
 	          productIDBC = entry.getKey();
 	          if(pKBUnite.get(productIDBC).equals("false") && pKCUnite.get(productIDBC).equals("true")){ 	          
 	          SPCode.put(productIDBC, "UCPACK");
 	          }else{
 	        	 SPCode.put(productIDBC, "UCUL");  
 	          } 	         
         }
         /** 100 Gram Unity **/
         for (Entry<String, Map<String,String>> entry : dataM.entrySet()) {
          	
             String productId = entry.getKey();
             Map<String,String> productDetails = entry.getValue();
             String dUnity = productDetails.getOrDefault("declaredUnity", "NO");        
             if(!declaredUnity.equals("NO")){         
            	 declaredUnity.put(productId, dUnity);  
             }
             String packaingValidatiion = productDetails.getOrDefault("itemdisplayNameP", "NO");
             packageValidation.put(productId, packaingValidatiion); 
             String receipeValidate = productDetails.getOrDefault("itemdisplayNameR", "NO");
             receipeValidatiion.put(productId, receipeValidate);
             String fatPerCode = productDetails.getOrDefault("fatPercentageInDryMatterMeasurementPrecisionCode", "NO");
             fatPercode.put(productId,fatPerCode);
            }
         
		try (BufferedReader br = new BufferedReader(
				new FileReader(Config))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					att.add(line.split("\\|"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] France={"fr-FR","fr-BE","fr-CA","fr-CH","fr-CI","fr-NG","fr-GA","fr-CG","fr-RE","fr-LU","fr-ML","fr-NE","fr-CM",
		"fr-DZ","fr-BJ","fr-BF","fr-BI","fr-KM","fr-CD","fr-DJ","fr-DJ","fr-GQ","fr-MG","fr-MG","fr-MC","fr-CF","fr-RW","fr-SN","fr-SC","fr-TD","fr-TG","fr-TN","fr-VU"};
		String[] German={"de-DE","de-BE","de-CH","de-AT","de-NG","de-LU","de-LI"};
		String[] Arabic={"ar-AE","ar-SA","ar-EG","ar-MA","ar-KW","ar-TN","ar-DZ","ar-IL","ar-LY","ar-IQ","ar-BH","ar-MR","ar-YE","ar-KM","ar-DJ",
				"ar-ER","ar-PS","ar-QA","ar-SO","ar-SD","ar-TD"};
		String[] Chinese={"ch-SG","ch-CH","zh-CN","zh-HK"};
		String[] English={"en-US","en-BE","en-GB","en-IN","en-ID","en-CA","en-IE","en-SG","en-PH","en-HK","en-NG","en-AU","en-NZ","en-GH","en-ZA",
			"en-CM","en-ZW","en-ZM","en-VU","en-TV","en-TT","en-TO","en-TZ","en-SS","en-SD","en-SK","en-SL","en-SC","en-SC","en-WS",
			"en-LC","en-VC","en-KN","en-RW","en-PG","en-PW","en-PK","en-UG","en-NR","en-NA","en-FM","en-MW","en-LR","en-LS","en-LS",
			"en-KE","en-SB","en-MH","en-GY","en-GD","en-GM","en-SZ","en-DM","es-CR","en-BW","en-BZ","en-BB","en-BS"};
		String[] Spanish ={"es-AR","es-ES","es-CL","es-UY","es-DO","es-MX","es-BO","es-CO","es-CR","es-EC","es-GT","es-GQ","es-HN","es-NI",
				"es-PA","es-PY","es-SV","es-VE"};
		String[] Italian ={"it-IT","it-CH","it-SM"};
		String[] Portuguese={"pt-BR","pt-PT","pt-AO","pt-CV","pt-GQ","pt-GW","pt-MZ","pt-ST","pt-TL"};
		String[] Dutch={"nl-NL","nl-BE"};
		String[] Greek={"el-GR","el-CY"};
		String[] Ro={"ro-RO","ro-MD"};String[] Ru={"ru-RU","ru-KZ","ru-BY","ru-KG"};String[] Sr={"sr-RS","sr-BA","sr-MNE"};String[] Sv={"sv-SE","sv-FI"};
		String[] Hr={"hr-BA","hr-HR"};String[] Ps={"ps-AF","ps-PK"};String[] Sw={"sw-TZ","sw-UG"}; 
		
		ArrayList<String> l3 = new ArrayList<>();
		l3.add("AITOR01");l3.add("ALAVE01");l3.add("BLEU11");l3.add("ALTA02");l3.add("KRAL03");l3.add("ARL01");l3.add("BANO01");l3.add("WEIS01");l3.add("FERRA01");l3.add("BLUE01");
		l3.add("DAVI01");l3.add("BUKO01");l3.add("MILK01");l3.add("EVEN01");l3.add("KIKI01");l3.add("LURP01");l3.add("PETR01");l3.add("MONB01");l3.add("BAKO01");l3.add("BPPFSP");
		l3.add("BONB01");l3.add("CASA01");l3.add("ELLE01");l3.add("GOYA01");l3.add("KIMS01");l3.add("MAUL01");l3.add("RICO01");l3.add("WEST02");l3.add("BPPBIO");l3.add("BPPOTH");
		l3.add("BPPBC");l3.add("CASTEL01");l3.add("EFFI02");l3.add("HOPE01");l3.add("KOLIB01");l3.add("MROZ01");l3.add("SOSA01");l3.add("WEST01");l3.add("BPPCHEVRE");l3.add("BPPPF");
		l3.add("PERE04");l3.add("DAMM01");l3.add("EURO04");l3.add("IOGO01");l3.add("LAYS01");l3.add("NATR01");l3.add("UNIF01");l3.add("CHOCO01");l3.add("BPPFON");l3.add("BPPPM");l3.add("BPPPP");
		
		List<String> Storage = new ArrayList<>();
		Storage.add("PL014");Storage.add("PL023");Storage.add("PL024");Storage.add("PL027");Storage.add("PL065");Storage.add("PL073");Storage.add("PL083");Storage.add("PL106");
		Storage.add("08");Storage.add("PL100");Storage.add("PL501");Storage.add("PE09");Storage.add("58");Storage.add("53");Storage.add("0");
		

		// Step 2: Create a new XML document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		// Root element
		Element rootElement = doc.createElement("STEP-ProductInformation");
		rootElement.setAttribute("ExportTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		rootElement.setAttribute("ExportContext", "Global");
		rootElement.setAttribute("ContextID", "Global");
		rootElement.setAttribute("WorkspaceID", "Main");
		rootElement.setAttribute("UseContextLocale", "false");
		doc.appendChild(rootElement);
		/*------------------------------- Qualifier xml added--------------------------------------  */
		 Document qualifierXml = docBuilder.parse(Qualifer);
	       Element staticContent = (Element) doc.importNode(qualifierXml.getDocumentElement(), true);
	       rootElement.appendChild(staticContent);
	     
		// Classifications
		/*Element classifications = doc.createElement("Classifications");
		rootElement.appendChild(classifications);
		*/
//		   /*--------ReplacementRules-----*/
			rootElement.appendChild(getReplacementRules(doc));		
			Element entities = doc.createElement("Entities");
			rootElement.appendChild(entities);
		
				// Products element
		Element productsElement = doc.createElement("Products");
	//	Element additionalproductElement = doc.createElement("Product");
	//	Element AddvariantElement = doc.createElement("Product");
		rootElement.appendChild(productsElement);		
		String recipeID = null;
		String packagingID = null;
	//	String nutrientID = null;		
		String RpeVariantServingSizeId=null;
		String RpeServingSize=null;
		String recipeGT = null;
		String recipeMQ = null;
		String PID=null;
		String PackageTradName =null;		
		HashSet<String> hsEntity100 = new HashSet<>();
		HashMap<String,String>  recipekey = new HashMap<>();
		HashMap<String,String>  recipeunity = new HashMap<>();
		HashMap<String,String> sSPrepar = new HashMap<>();
		HashSet<String> uniquReceipeID= new HashSet<>();
		HashSet<String> uniquPackageID= new HashSet<>();
		HashMap<String,String>  compositionIndex = new HashMap<>();
		for (Entry<String, List<String[]>> entry : recipeMap.entrySet()) {
			String ErrorPath=null;
			try{
			String productId = entry.getKey();
		//	if(!uniquReceipeID.contains(RecipeID.get(productId))){
			
		    String SCrecipeid=productId.substring(0, productId.length()-2);
			if("GRA02".equals(grade.get(productId.substring(0, productId.length()-2)))){
				recipeID="SR"+SCrecipeid.substring(2);
			}else if("SFUC".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))){
				recipeID ="SR"+SCrecipeid.substring(2);
				//PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));			
			}else{
				recipeID = RecipeID.get(productId);
			}
			uniquReceipeID.add(recipeID);
			PID=userTypeID(ProdId.get(productId.substring(0, productId.length()-2)));			
			recipeGT =groupTechnology.get(productId.substring(0, productId.length()-2));
			recipeMQ =marquePrincipale.get(productId.substring(0, productId.length()-2));			
			if(((!"Unknown".equals(recipeID)) && !receipeValidatiion.get(SCrecipeid).equals("NO")) || recipeID.contains("SR") ){			
			List<String[]> productDetails = entry.getValue();
			String UserTypeID = userTypeID(ProdId.get(productId));			
			Element productElement = doc.createElement("Product");
			Element valuesElement = doc.createElement("Values");
			Element keyValue = doc.createElement("KeyValue");
			Element enititykeyValue = doc.createElement("KeyValue");			
			Element name = doc.createElement("Name");
			productElement.appendChild(name);
			Element recipeEntityRef = doc.createElement("EntityCrossReference");		
			productElement.setAttribute("UserTypeID", UserTypeID);
			productElement.setAttribute("ParentID","PPH_"+recipeGT+"_"+recipeMQ);
			keyValue.setAttribute("KeyID", "Recipe_Key");
			keyValue.setTextContent(recipeID);
			productElement.appendChild(keyValue);
			recipeEntityRef.setAttribute("Type", "Rpe_Recipe_ServingSize");
			enititykeyValue.setAttribute("KeyID","SS_Key");
			enititykeyValue.setTextContent( recipeID+"_100");
			recipeEntityRef.appendChild(enititykeyValue);
			productsElement.appendChild(productElement);
			Element keyAttSSID = doc.createElement("Value");
			keyAttSSID.setAttribute("AttributeID", "Att_Recipe_ID");
			keyAttSSID.setTextContent(recipeID);
			valuesElement.appendChild(keyAttSSID);
			Element recipeconstantElement = doc.createElement("Value");
			Element recipeconstantElement1 = doc.createElement("Value");
			Element recipeconstantElement2 = doc.createElement("Value");
			Element recipeconstantElement3 = doc.createElement("Value");
			Element recipeconstantElement4 = doc.createElement("Value");
			productElement.appendChild(recipeEntityRef);
			productElement.appendChild(valuesElement);
			HashSet<String> hsEU = new HashSet<>();			
			HashSet<String> hs = new HashSet<>();
			HashSet<String> sSuniqueID = new HashSet<>();
			/*------ Ingredients  -----------*/			
			HashSet<String> hsContainers = new HashSet<>();
			Element multiDataContainerElement = null;
			HashSet<String> hsContainersEU = new HashSet<>();
			Element multiDataContainerElementEU = null;
			Element valuesMR2Element = null;			
			HashSet<String> hsPhyContainer = new HashSet<>();			
			 Element ingredients = doc.createElement("EntityCrossReference");
		      	Element ingMetaData = doc.createElement("MetaData");		      
		      	recipeconstantElement1.setAttribute("AttributeID", "Att_Is_Alg_Rel_Data_Prov");
		      	recipeconstantElement1.setAttribute("ID","Y");
		      	recipeconstantElement2.setAttribute("AttributeID", "Att_Lvl_Of_Cont_cod");
		      	recipeconstantElement2.setTextContent("CONTAINS");
		      	recipeconstantElement3.setAttribute("AttributeID", "Att_Milk_acq_Typ_cod");
		      	recipeconstantElement3.setAttribute("ID","DAIRY");
		      	recipeconstantElement4.setAttribute("AttributeID", "Att_Src_Sys");
		      	recipeconstantElement4.setTextContent("SPHERE");
		   	valuesElement.appendChild(recipeconstantElement1);
		    valuesElement.appendChild(recipeconstantElement3);
		    valuesElement.appendChild(recipeconstantElement4);
		   	ingMetaData.appendChild(recipeconstantElement2);
		      	ingredients.appendChild(ingMetaData);
		      	
         Element entyCrossRefElementMR17 = doc.createElement("EntityCrossReference");
         Element entyCrossRefElementMR17SS = doc.createElement("EntityCrossReference");
         Element entyCrossRefElementMR17EU = doc.createElement("EntityCrossReference");
         Element entyCrossRefElementMR17EUServ = doc.createElement("EntityCrossReference");
         Element entyCrossRefElementSSNutrient = doc.createElement("EntityCrossReference");
          
         Element dataContainersElement = doc.createElement("DataContainers");
     	Element metaDataElementMWR17 = doc.createElement("MetaData");
     	Element metaDataElementMWR17EU = doc.createElement("MetaData");
     	Element metaDataElementMWR17mr = doc.createElement("MetaData");     	
     	Element metaDataSSNutrient = doc.createElement("MetaData");     	
     	Element valueElementMR17 = doc.createElement("Value");
     	Element valueElementMR18 = doc.createElement("Value");
     	Element valueElementVNType = doc.createElement("Value");
     	Element valueElementVNClm = doc.createElement("Value");
     	Element valueElementMR17EU = doc.createElement("Value");     	
     	Element productElementEntity = doc.createElement("Entity");
     	Element dCTMDC = doc.createElement("MultiDataContainer");
     	dCTMDC.setAttribute("Type", "Dct_Physico_Chemical_Criteria");
     	Element dCTMDCMD = null;
     	Element dCTMDCMDMV = null;
     	int i=0; int j=0; int k=0; int m=0;
     	String nNc=null;
     	String maxVal=null;
     	String warnVal=null;
     	String maxAttribut=null;
     	String warAttribut=null;
     	String mmaxVal=null;
     	String mwarnVal=null;
     	String mmaxAttribut=null;
     	String mwarAttribut=null;
     	String srdvalue=null;
     	String ssValue=null;
     	String s100unity=null;
     	String SValue =null;
     	String sIndex=null;
     	String uIndex=null;
     	Element catchFish = doc.createElement("MultiValue");     

		//JULY7---ATT_DIS_ON_PACK---START---pre-scan lookup for MRAUTRES onPack Boolean
		/*
		 * MRAUTRES Att_Dis_on_Pack lookup.
		 * Requirement:
		 * If Att_Qty_cont is sent and Att_Dis_on_Pack/onPack is provided,
		 * use the SPHERE Boolean. If Att_Dis_on_Pack/onPack is missing,
		 * default Att_Dis_on_Pack to Y.
		 *
		 * Key format: nutrientIndex|nutrientCode
		 * Example: 0|CA -> true, 0|P -> false
		 */
		Map<String, String> mrautresOnPackMap = new HashMap<>();
		Map<String, String> mrautresCurrentCodeByIndex = new HashMap<>();
		Map<String, String> preMrautresCurrentCodeByIndex = new HashMap<>();

		for (String[] preDetail : productDetails) {
			String preValue = preDetail[2];
			String preLValue = preDetail[3];

			if (preValue.contains("nutrimentsAutres/")) {
				String nutrientIndex = "";

				if (preValue.contains("nutriments#")
						&& preValue.contains("/nutrimentsAutres/")) {
					int startPos = preValue.indexOf("nutriments#") + "nutriments#".length();
					int endPos = preValue.indexOf("/nutrimentsAutres/");
					nutrientIndex = preValue.substring(startPos, endPos);
				}

				if (preValue.contains("/code")) {
					preMrautresCurrentCodeByIndex.put(nutrientIndex, preLValue);
				}

				if (preValue.contains("/onPack")) {
					String currentCode = preMrautresCurrentCodeByIndex.get(nutrientIndex);

					if (currentCode != null) {
						String nutrientKey = nutrientIndex + "|" + currentCode;
						mrautresOnPackMap.put(nutrientKey, preLValue);
					}
				}
			}
		}
		//JULY7---ATT_DIS_ON_PACK---END---pre-scan lookup for MRAUTRES onPack Boolean

		/*
		 * MR17 nutritional claim type lookup by index.
		 * Fixes carry-forward issue where a previous claim type, for example HIGH for CALCIUM,
		 * could be incorrectly reused for another nutrient element, for example PROTEIN.
		 * Example mapping:
		 * nutritionalClaimType#1 -> HIGH
		 * nutritionalClaimType#2 -> SOURCE_OF
		 */
		
		//july7---1st fix---MR17
		Map<String, String> mr17ClaimTypeByIndex = new HashMap<>();

		for (String[] preDetail : productDetails) {
			String preValue = preDetail[2];
			String preLValue = preDetail[3];

			if (preValue.contains("nutritionalClaimType#")
					&& !preValue.contains("VariantnutritionalClaimType#")) {
				String claimIndex = "";
				int hashPos = preValue.indexOf("#");
				if (hashPos != -1) {
					int startPos = hashPos + 1;
					int endPos = startPos;
					while (endPos < preValue.length()
							&& Character.isDigit(preValue.charAt(endPos))) {
						endPos++;
					}
					claimIndex = preValue.substring(startPos, endPos);
				}
				mr17ClaimTypeByIndex.put(claimIndex, preLValue);
			}
		}
     	
			for (String[] detail : productDetails) {
				String group = detail[0];
				String product = detail[1];
				String value = detail[2];
				String lValue = detail[3];
				String fullPath = group + "_" + product + "_" + value;			
				ErrorPath = group +"|"+PID+ "|" + product + "|" + value + "|"+lValue;
					String mr17Path = fullPath.replaceAll("#\\d+","");				
					String servType="";
					String rName= group+"_"+value;
					//System.out.println("rName--------------"+rName);
					//if(value.equals("itemdisplayNameR")){
					if(rName.equals("Caracteristiques_Produits_Spec_nom")){
						name.setTextContent(lValue);
	                }/*else{
	                	name.setTextContent(SCDName.get(productId.substring(0, productId.length()-2)));
	                }*/					
					
				for (String[] values : att) {
					entity = values[0]; // prod/var/package
					category = values[1];
					Configpath = values[2];
					attrib = values[3];			
					String sSIndex= null;
					String cNIndex= null;					
					if(fullPath.contains("servingSize_value")){	
						ssValue=lValue.replace(",", ".");
						SValue= servingsize.get(productId.substring(0,8)).replace(",", ".");
						if(SValue.substring(0,SValue.length()-2).trim().equals(ssValue)){							
						String[] indexObj = splitInputData(fullPath);											
						 sIndex = indexObj[1];						
						}						
					}
					if(fullPath.contains("servingSize_unity") ){						
						sSIndex=fullPath.substring(fullPath.length()-1);
						String[] indexObj = splitInputData(fullPath);
						uIndex=indexObj[1];
					}
					
					if(fullPath.contains("composition_nutriments")){							
						cNIndex=value.substring(11,12);
					}
					
					if(fullPath.contains("composition_isPrepared")){						
						String sSP=fullPath.substring(fullPath.length()-1);
						String Product = productId.substring(0, productId.length()-2);
						if(sSP.equals("1"))
						{	
							sSPrepar.put(Product,lValue);						
						}
					}
					if(fullPath.contains("servingSize_unity")&& sSIndex.equals(sIndex)){
						servType=productId+"_"+lValue;
						//System.out.println("servType-----------"+servType);
					}					
					if(fullPath.contains("composition_nutriments") && cNIndex.equals("0") ){
						servType=productId+"N";
					}
					if(fullPath.contains("composition_nutriments") && cNIndex.equals("1") ){
						servType=productId+"CN";
					}
					if(fullPath.contains("composition_nutriments") && cNIndex.equals("2") ){
						servType=productId+"C2";
					}
					if(fullPath.contains("composition_nutriments") && cNIndex.equals("3") ){
						servType=productId+"C3";
					}
					if(fullPath.contains("servingSize_unity")&& sSIndex.equals("1")){
					servType=productId+"V";	
					}
					if(fullPath.contains("servingSize_unity")&& sSIndex.equals("2")){
					servType=productId+"_2"+ssValue;
					}
					if(fullPath.contains("servingSize_unity")&& sSIndex.equals("3")){
						servType=productId+"_3"+ssValue;	
					}
					if(fullPath.contains("servingSize_unity")&& sSIndex.equals("4")){
						servType=productId+"_4"+ssValue;	
					}
								
					if(value.equals("numberOfServingsRangeDescription")){					
						srdvalue=lValue;
					}
					
                   if(value.equals("preservationTechniqueTypeCode") && lValue.equals("ATTESTED_MILK")){
                	   recipeconstantElement.setAttribute("AttributeID", "Att_Ing_Of_Cnc_cod");
       		      	recipeconstantElement.setTextContent("RAW MILK");
                	   
                   }                   
                   Element valuesElementEntity1 = doc.createElement("Values");                                  
                   
					if(!(servType.equals("")) && !(hsEntity100.contains(servType)) ){
						hsEntity100.add(servType);					
					if (!valueElements.containsKey(productId)) {
						valueElements.put(productId, new ArrayList<>());
					}
					String servSize="";
					Element entyCrossRefElement = doc.createElement("EntityCrossReference");
					Element rRSValue = doc.createElement("KeyValue");
					
					if(!fullPath.contains("servingSize_unity")){						
						entyCrossRefElement.setAttribute("Type", "Rpe_Recipe_ServingSize");
						servSize="100";
					}
					rRSValue.setAttribute("KeyID", "SS_Key");					
					if(fullPath.contains("servingSize_unity")  && sSIndex.equals(sIndex)){
						rRSValue.setTextContent(recipeID+ssValue);	
					}
					else if(fullPath.contains("servingSize_unity")  && sSIndex.equals("1")){
						rRSValue.setTextContent(recipeID+ssValue+"01");
					}else if(fullPath.contains("servingSize_unity")  && sSIndex.equals("2")){
						rRSValue.setTextContent(recipeID+ssValue+"01");
					}else if(fullPath.contains("servingSize_unity")  && sSIndex.equals("3")){
						rRSValue.setTextContent(recipeID+ssValue+"01");
					}else if(fullPath.contains("servingSize_unity")  && sSIndex.equals("4")){
						rRSValue.setTextContent(recipeID+ssValue+"01");
					}
					else{					
						if(cNIndex.equals("0")){
					rRSValue.setTextContent(recipeID+"_100");
						}else{
							rRSValue.setTextContent(recipeID+ssValue+"01");
						}
					}					
					entyCrossRefElement.appendChild(rRSValue);	
					productElementEntity = doc.createElement("Entity");				
					productElementEntity.setAttribute("UserTypeID", "Serving_Size");
					productElementEntity.setAttribute("ParentID", "Nutrients");
					entities.appendChild(productElementEntity);
					Element sSname = doc.createElement("Name");
					productElementEntity.appendChild(sSname);
					Element nkeyValue = doc.createElement("KeyValue");
					nkeyValue.setAttribute("KeyID", "SS_Key");
					productElementEntity.appendChild(nkeyValue);					
					productElementEntity.appendChild(valuesElementEntity1);				
					
					String Productkey = productId.substring(0, productId.length()-2);					
					RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_"+ssValue;					
					if(fullPath.contains("servingSize_unity") && sSIndex.equals(sIndex)){
						if(sSIndex.equals("0")){
						nkeyValue.setTextContent(RpeVariantServingSizeId.replace(",", "."));
						recipekey.put(Productkey,RpeVariantServingSizeId);						
						recipeunity.put(Productkey, ssValue);
						sSname.setTextContent("Serving_Size_SS_Key_"+RpeVariantServingSizeId.replace(",", "."));
						}else{
							RpeServingSize=productId.substring(0, productId.length()-2)+"001"+"_"+sIndex+"_"+ssValue;
							nkeyValue.setTextContent(RpeServingSize.replace(",", "."));
							recipekey.put(Productkey+sIndex,RpeServingSize.replace(",", "."));						
							recipeunity.put(Productkey, ssValue);
							sSname.setTextContent("Serving_Size_SS_Key_"+RpeServingSize.replace(",", "."));
						}
					}
					else{
						if(cNIndex.equals("0")){
						nkeyValue.setTextContent(recipeID+"_100");
						sSname.setTextContent("Serving_Size_SS_Key_"+recipeID+"_100");
						}else if(cNIndex.equals("1")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_1_100";
							nkeyValue.setTextContent(RpeVariantServingSizeId);
							sSname.setTextContent("Serving_Size_SS_Key_"+RpeVariantServingSizeId.replace(",", "."));
							compositionIndex.put(productId.substring(0, productId.length()-2)+"1", RpeVariantServingSizeId);
						
						}else if(cNIndex.equals("2")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_2_100";
							nkeyValue.setTextContent(RpeVariantServingSizeId);
							sSname.setTextContent("Serving_Size_SS_Key_"+RpeVariantServingSizeId.replace(",", "."));
							compositionIndex.put(productId.substring(0, productId.length()-2)+"2", RpeVariantServingSizeId);
						}else if(cNIndex.equals("3")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_3_100";
							nkeyValue.setTextContent(RpeVariantServingSizeId);
							sSname.setTextContent("Serving_Size_SS_Key_"+RpeVariantServingSizeId.replace(",", "."));
							compositionIndex.put(productId.substring(0, productId.length()-2)+"3", RpeVariantServingSizeId);
						}
					}
					Element sSkeyValue = doc.createElement("Value");
					sSkeyValue.setAttribute("AttributeID", "Att_SS_ID");					
					if(fullPath.contains("servingSize_unity") && sSIndex.equals("0")){							
					sSkeyValue.setTextContent(RpeVariantServingSizeId.replace(",", "."));
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("1")){					
						sSkeyValue.setTextContent(RpeServingSize.replace(",", "."));
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("2")){					
						sSkeyValue.setTextContent(RpeServingSize.replace(",", "."));
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("3")){					
						sSkeyValue.setTextContent(RpeServingSize.replace(",", "."));
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("4")){					
						sSkeyValue.setTextContent(RpeServingSize.replace(",", "."));
					}
					else{
						if(cNIndex.equals("0")){
						sSkeyValue.setTextContent(recipeID+"_100");
						}else if(cNIndex.equals("1")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_1_100";
							sSkeyValue.setTextContent(RpeVariantServingSizeId);
							//recipekey.put(Productkey+"100",RpeVariantServingSizeId);
						}else if(cNIndex.equals("2")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_2_100";
							sSkeyValue.setTextContent(RpeVariantServingSizeId);
						}
						else if(cNIndex.equals("3")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_3_100";
							sSkeyValue.setTextContent(RpeVariantServingSizeId);
						}
						else if(cNIndex.equals("4")){
							RpeVariantServingSizeId=productId.substring(0, productId.length()-2)+"001"+"_4_100";
							sSkeyValue.setTextContent(RpeVariantServingSizeId);
						}
					}
					valuesElementEntity1.appendChild(sSkeyValue);					
					Element pElementEntity = doc.createElement("Value");
					pElementEntity.setAttribute("AttributeID", "Att_Hsld_Serv_size");		
						if(fullPath.contains("servingSize_unity") && sSIndex.equals("0")){
						pElementEntity.setAttribute("UnitID", unityMeasure(lValue));
					//	System.out.println("unity------------"+unityMeasure(lValue));
						if(Objects.nonNull(ssValue)){
						pElementEntity.setTextContent(ssValue.replace("," , "."));
						}
						
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("1")){
						pElementEntity.setAttribute("UnitID", unityMeasure(lValue));
						if(Objects.nonNull(ssValue)){
						pElementEntity.setTextContent(ssValue.replace("," , "."));
						}
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("2")){
						pElementEntity.setAttribute("UnitID", unityMeasure(lValue));
						if(Objects.nonNull(ssValue)){
						pElementEntity.setTextContent(ssValue.replace("," , "."));
						}
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("3")){
						pElementEntity.setAttribute("UnitID", unityMeasure(lValue));
						if(Objects.nonNull(ssValue)){
						pElementEntity.setTextContent(ssValue.replace("," , "."));
						}
					}else if(fullPath.contains("servingSize_unity") && sSIndex.equals("4")){
						pElementEntity.setAttribute("UnitID", unityMeasure(lValue));
						if(Objects.nonNull(ssValue)){
						pElementEntity.setTextContent(ssValue.replace("," , "."));
						}
					}
					else{
						s100unity= declaredUnity.get(productId.substring(0,8));
						pElementEntity.setAttribute("UnitID", unityMeasure(s100unity));
						pElementEntity.setTextContent("100");
					}					
					valuesElementEntity1.appendChild(pElementEntity);
					Element sphere = doc.createElement("Value");
					sphere.setAttribute("AttributeID", "Att_Src_Sys");
					sphere.setTextContent("SPHERE");
					valuesElementEntity1.appendChild(sphere);
					}
 				  /*-------- Serving Size 100-------------*/
				/*	if (mr17Path.equals(Configpath) && category.equals("MR17")) {
						String attribObj =null;
					
						String[] indexObj = splitInputData(fullPath);
						
						String index = indexObj[1];						
						index = index.replaceAll("[a-z]", "");						
						index = index.replaceAll("_", "");						
						if(mr17Path.contains("value")||mr17Path.contains("nutritionalClaimNutrientElement")){						
							if (!valueElements.containsKey(productId)) {
								valueElements.put(productId, new ArrayList<>());
							}
							metaDataElementMWR17 = doc.createElement("MetaData");
							
								 attribObj = splitInputDataSlash(value);
								entyCrossRefElementMR17 = doc.createElement("EntityCrossReference");
								if(mr17Path.contains("value")){
								entyCrossRefElementMR17.setAttribute("EntityID", attribObj);
								
								}else{
									
									if(attrib.equals("Att_Nut_Clm_elt")){	
										entyCrossRefElementMR17.setAttribute("EntityID", nutriant(lValue));
									}
								}
								if(lValue.equals("POLYPHOSPHATE")){
									entyCrossRefElementMR17.setAttribute("Type", "Rpe_NonNutrientClaims");
								}
								else{
								   entyCrossRefElementMR17.setAttribute("Type", "Ree_ServingSize_Nutrients");
								   productElementEntity.appendChild(entyCrossRefElementMR17);
								}
								j++;
						}
						if(j>=1){
							
						if(!(mr17Path.contains("code")) && !(mr17Path.contains("nutritionalClaimDetail"))){
						valueElementMR17 = doc.createElement("Value");					
						valueElementMR17.setAttribute("AttributeID", attrib.trim());
						valueElementMR17.setAttribute("QualifierID", "AllCountries");						
						if(attrib.equals("Att_Meas_prec_cod")|| attrib.equals("Att_Daily_val_int_per_meas_prec")){
							valueElementMR17.setAttribute("ID", lValue);
						}else{
							valueElementMR17.setTextContent(lValue);
							if(attrib.equals("Att_Qty_cont")){
								Element	grossValue = doc.createElement("Value");
								grossValue.setAttribute("AttributeID", "Att_Gross_value");
								grossValue.setTextContent(lValue);
								metaDataElementMWR17.appendChild(grossValue);
								}
						}
						metaDataElementMWR17.appendChild(valueElementMR17);
						entyCrossRefElementMR17.appendChild(metaDataElementMWR17);	
						productElementEntity.appendChild(entyCrossRefElementMR17);
						
						}else{
							if(attrib.equals("Att_Nut_Clm_Typ")){
								
								nNc =lValue;
							}
							if(attrib.equals("Att_Nut_Clm_elt")){	
								valueElementMR17 = doc.createElement("Value");
									valueElementMR17.setAttribute("AttributeID", attrib.trim());
									valueElementMR17.setAttribute("QualifierID", "AllCountries");						
									valueElementMR17.setAttribute("ID",lValue);
									valueElementMR18 = doc.createElement("Value");
									valueElementMR18.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
									valueElementMR18.setAttribute("QualifierID", "AllCountries");						
									valueElementMR18.setAttribute("ID",nNc);
									//kani
									Element	dis_on_pack = doc.createElement("Value");
									dis_on_pack.setAttribute("AttributeID", "Att_Clm_Typ_Dis_on_Pack");
									dis_on_pack.setAttribute("QualifierID", "AllCountries");
									dis_on_pack.setAttribute("ID", "Y");
									metaDataElementMWR17.appendChild(valueElementMR17);
									metaDataElementMWR17.appendChild(valueElementMR18);
									entyCrossRefElementMR17.appendChild(metaDataElementMWR17);
									if(lValue.equals("POLYPHOSPHATE")){
										productElement.appendChild(entyCrossRefElementMR17);
									}
									else{
										productElementEntity.appendChild(entyCrossRefElementMR17);
									}
								}
							
						}
					}
						if(j<1){
							metaDataElementMWR17 = doc.createElement("MetaData");
    						 attribObj = splitInputDataSlash(value);
    						entyCrossRefElementMR17 = doc.createElement("EntityCrossReference");
    						entyCrossRefElementMR17.setAttribute("EntityID", attribObj);    						
    						entyCrossRefElementMR17.setAttribute("Type", "Ree_ServingSize_Nutrients");
    						productElementEntity.appendChild(entyCrossRefElementMR17);
    						valueElementMR17 = doc.createElement("Value");
    						valueElementMR17.setAttribute("AttributeID", attrib); 
    						valueElementMR17.setAttribute("QualifierID", "AllCountries");
    						if(attrib.equals("Att_Meas_prec_cod")|| attrib.equals("Att_Daily_val_int_per_meas_prec")){
    							valueElementMR17.setAttribute("ID", lValue);
    						}else{
    							valueElementMR17.setTextContent(lValue);
    							if(attrib.equals("Att_Qty_cont")){
    								Element	grossValue = doc.createElement("Value");
    								grossValue.setAttribute("AttributeID", "Att_Gross_value");
    								grossValue.setTextContent(lValue);
    								metaDataElementMWR17.appendChild(grossValue);
    								}
    						}
    						metaDataElementMWR17.appendChild(valueElementMR17);
    						entyCrossRefElementMR17.appendChild(metaDataElementMWR17);    						
    						productElementEntity.appendChild(entyCrossRefElementMR17);
    						
						}
						
					}
*/					
					if (mr17Path.equals(Configpath) && category.equals("MR17")) {
					    String attribObj = null;

					    String[] indexObj = splitInputData(fullPath);

					    String index = indexObj[1];
					    index = index.replaceAll("[a-z]", "");
					    index = index.replaceAll("_", "");

					    if (mr17Path.contains("value") || mr17Path.contains("nutritionalClaimNutrientElement")) {

					        if (!valueElements.containsKey(productId)) {
					            valueElements.put(productId, new ArrayList<>());
					        }

					        metaDataElementMWR17 = doc.createElement("MetaData");

					        attribObj = splitInputDataSlash(value);
					        entyCrossRefElementMR17 = doc.createElement("EntityCrossReference");

					        if (mr17Path.contains("value")) {
					            entyCrossRefElementMR17.setAttribute("EntityID", attribObj);
					        } else {

					            if (attrib.equals("Att_Nut_Clm_elt")) {
					                entyCrossRefElementMR17.setAttribute("EntityID", nutriant(lValue));
					            }
					        }

					        if (lValue.equals("POLYPHOSPHATE")) {
					            entyCrossRefElementMR17.setAttribute("Type", "Rpe_NonNutrientClaims");
					        } else {
					            entyCrossRefElementMR17.setAttribute("Type", "Ree_ServingSize_Nutrients");
					            productElementEntity.appendChild(entyCrossRefElementMR17);
					        }

					        j++;
					    }

					    if (j >= 1) {

					        if (!(mr17Path.contains("code")) && !(mr17Path.contains("nutritionalClaimDetail"))) {

					            valueElementMR17 = doc.createElement("Value");
					            valueElementMR17.setAttribute("AttributeID", attrib.trim());
					            valueElementMR17.setAttribute("QualifierID", "AllCountries");

					            if (attrib.equals("Att_Meas_prec_cod")
					                    || attrib.equals("Att_Daily_val_int_per_meas_prec")) {

					                valueElementMR17.setAttribute("ID", lValue);

					            } else {

					                valueElementMR17.setTextContent(lValue);

					                if (attrib.equals("Att_Qty_cont")) {
					                    Element grossValue = doc.createElement("Value");
					                    grossValue.setAttribute("AttributeID", "Att_Gross_value");
					                    grossValue.setTextContent(lValue);
					                    metaDataElementMWR17.appendChild(grossValue);
					                }
					            }

					            metaDataElementMWR17.appendChild(valueElementMR17);
					            entyCrossRefElementMR17.appendChild(metaDataElementMWR17);
					            productElementEntity.appendChild(entyCrossRefElementMR17);

					        } else {
					            if (attrib.equals("Att_Nut_Clm_Typ")) {

					                /*
					                 * Store current claim type as before.
					                 * The indexed lookup below is used for the actual claim element output
					                 * to avoid carrying CALCIUM type into PROTEIN or vice versa.
					                 */
					                nNc = lValue;
					            }

					            if (attrib.equals("Att_Nut_Clm_elt")) {

					                String claimIndex = "";
					                int hashPos = value.indexOf("#");

					                if (hashPos != -1) {
					                    int startPos = hashPos + 1;
					                    int endPos = startPos;

					                    while (endPos < value.length()
					                            && Character.isDigit(value.charAt(endPos))) {
					                        endPos++;
					                    }

					                    claimIndex = value.substring(startPos, endPos);
					                }

					                /*
					                 * Requirement fix:
					                 * Att_Nut_Clm_elt#N must use Att_Nut_Clm_Typ#N.
					                 * This prevents previous claim type values from being reused for another index.
					                 */
					                nNc = mr17ClaimTypeByIndex.get(claimIndex);

					                valueElementMR17 = doc.createElement("Value");
					                valueElementMR17.setAttribute("AttributeID", attrib.trim());
					                valueElementMR17.setAttribute("QualifierID", "AllCountries");
					                valueElementMR17.setAttribute("ID", lValue);

					                metaDataElementMWR17.appendChild(valueElementMR17);

					                if (nNc != null && !nNc.trim().isEmpty()) {

					                    valueElementMR18 = doc.createElement("Value");
					                    valueElementMR18.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
					                    valueElementMR18.setAttribute("QualifierID", "AllCountries");
					                    valueElementMR18.setAttribute("ID", nNc);
					                    metaDataElementMWR17.appendChild(valueElementMR18);

					                    /*
					                     * IF SPHERE sends Att_Nut_Clm_Typ for this claim index,
					                     * THEN Att_Clm_Typ_Dis_on_Pack is TRUE / Y.
					                     */
					                    Element dis_on_pack = doc.createElement("Value");
					                    dis_on_pack.setAttribute("AttributeID", "Att_Clm_Typ_Dis_on_Pack");
					                    dis_on_pack.setAttribute("QualifierID", "AllCountries");
					                    dis_on_pack.setAttribute("ID", "Y");

					                    metaDataElementMWR17.appendChild(dis_on_pack);
					                }

					                entyCrossRefElementMR17.appendChild(metaDataElementMWR17);

					                if (lValue.equals("POLYPHOSPHATE")) {
					                    productElement.appendChild(entyCrossRefElementMR17);
					                } else {
					                    productElementEntity.appendChild(entyCrossRefElementMR17);
					                }
					            }
					        }
					    }

					    if (j < 1) {

					        metaDataElementMWR17 = doc.createElement("MetaData");

					        attribObj = splitInputDataSlash(value);

					        entyCrossRefElementMR17 = doc.createElement("EntityCrossReference");
					        entyCrossRefElementMR17.setAttribute("EntityID", attribObj);
					        entyCrossRefElementMR17.setAttribute("Type", "Ree_ServingSize_Nutrients");

					        productElementEntity.appendChild(entyCrossRefElementMR17);

					        valueElementMR17 = doc.createElement("Value");
					        valueElementMR17.setAttribute("AttributeID", attrib);
					        valueElementMR17.setAttribute("QualifierID", "AllCountries");

					        if (attrib.equals("Att_Meas_prec_cod")
					                || attrib.equals("Att_Daily_val_int_per_meas_prec")) {

					            valueElementMR17.setAttribute("ID", lValue);

					        } else {

					            valueElementMR17.setTextContent(lValue);

					            if (attrib.equals("Att_Qty_cont")) {
					                Element grossValue = doc.createElement("Value");
					                grossValue.setAttribute("AttributeID", "Att_Gross_value");
					                grossValue.setTextContent(lValue);
					                metaDataElementMWR17.appendChild(grossValue);
					            }
					        }

					        metaDataElementMWR17.appendChild(valueElementMR17);
					        entyCrossRefElementMR17.appendChild(metaDataElementMWR17);
					        productElementEntity.appendChild(entyCrossRefElementMR17);
					    }
					}
					
					if (mr17Path.equals(Configpath) && category.equals("MRSERSIZE")) {						
						SValue= servingsize.get(productId.substring(0,8));
						SValue=SValue.replace(",", ".");						
						if(SValue.substring(0,SValue.length()-2).trim().equals(ssValue.trim()) && sIndex.equals(uIndex)){							
						if(!(attrib.equals("No_attribute"))){							
						if(mr17Path.contains("value") && !mr17Path.contains("other/value")){						
							metaDataElementMWR17mr = doc.createElement("MetaData");
						String attribObj = splitInputDataSlash(value);
						entyCrossRefElementMR17SS = doc.createElement("EntityCrossReference");						
						entyCrossRefElementMR17SS.setAttribute("EntityID", attribObj);						
						entyCrossRefElementMR17SS.setAttribute("Type", "Ree_ServingSize_Nutrients");
							i++;							
						productElementEntity.appendChild(entyCrossRefElementMR17SS);
						}
						if(mr17Path.contains("other/code")){							
								metaDataElementMWR17mr = doc.createElement("MetaData");							
							entyCrossRefElementMR17SS = doc.createElement("EntityCrossReference");						
							entyCrossRefElementMR17SS.setAttribute("EntityID", lValue);						
							entyCrossRefElementMR17SS.setAttribute("Type", "Ree_ServingSize_Nutrients");														
							productElementEntity.appendChild(entyCrossRefElementMR17SS);
							}
											
						if(i>=1){
						if(!(mr17Path.contains("code"))){
						valueElementMR17 = doc.createElement("Value");
						valueElementMR17.setAttribute("AttributeID", attrib);	
						valueElementMR17.setAttribute("QualifierID", "AllCountries");						
						if(attrib.equals("Att_Daily_val_int_per_meas_prec")){							
							valueElementMR17.setAttribute("ID", lValue);
						}else if(attrib.equals("Att_Meas_prec_cod")){						
							valueElementMR17.setAttribute("ID", lValue);							
						}else{
							
							valueElementMR17.setTextContent(lValue);
						}
                        if (attrib.equals("Att_Qty_cont")) {
                                Element grossValue = doc.createElement("Value");
                                grossValue.setAttribute("AttributeID", "Att_Gross_value");
                                grossValue.setTextContent(lValue);
                                metaDataElementMWR17mr.appendChild(grossValue);
                            }
                        
						metaDataElementMWR17mr.appendChild(valueElementMR17);
						entyCrossRefElementMR17SS.appendChild(metaDataElementMWR17mr);						
						productElementEntity.appendChild(entyCrossRefElementMR17SS);
						}
						}
												
                        if(i<1){
                        	metaDataElementMWR17mr = doc.createElement("MetaData");
    						String attribObj = splitInputDataSlash(value);
    						entyCrossRefElementMR17SS = doc.createElement("EntityCrossReference");
    						entyCrossRefElementMR17SS.setAttribute("EntityID", attribObj);    						
    						entyCrossRefElementMR17SS.setAttribute("Type", "Ree_ServingSize_Nutrients");
    						productElementEntity.appendChild(entyCrossRefElementMR17SS);
    						valueElementMR17 = doc.createElement("Value");
    						valueElementMR17.setAttribute("AttributeID", attrib);
    						valueElementMR17.setAttribute("QualifierID", "AllCountries");
    						if(attrib.equals("Att_Daily_val_int_per_meas_prec")){    						
    							valueElementMR17.setAttribute("ID", lValue);
    						}else if(attrib.equals("Att_Meas_prec_cod")){
    						
    							valueElementMR17.setAttribute("ID", lValue);							
    						}else{
    							
    							valueElementMR17.setTextContent(lValue);
    						}
    						
    						metaDataElementMWR17mr.appendChild(valueElementMR17);
    						entyCrossRefElementMR17SS.appendChild(metaDataElementMWR17mr);    						
    						productElementEntity.appendChild(entyCrossRefElementMR17SS);
						}
                      
						}
					}
					}
					/*------Serving Size 100------*/
					/*
					//JULY7---ATT_DIS_ON_PACK---START---MRAUTRES output logic
					if (mr17Path.equals(Configpath) && category.equals("MRAUTRES")) {
						if(mr17Path.contains("code")){
 					    metaDataElementMWR17EU = doc.createElement("MetaData");
						String attribObj = splitInputDataSlash(value);
						entyCrossRefElementMR17EU = doc.createElement("EntityCrossReference");
						entyCrossRefElementMR17EU.setAttribute("EntityID", lValue);
						entyCrossRefElementMR17EU.setAttribute("Type", "Ree_ServingSize_Nutrients");						
						productElement.appendChild(entyCrossRefElementMR17EU);
						m++;
						}
						if(m>0){
						if(!(mr17Path.contains("code"))){
							
							valueElementMR17EU = doc.createElement("Value");
							valueElementMR17EU.setAttribute("AttributeID", attrib);
							valueElementMR17EU.setAttribute("QualifierID", "AllCountries");
							 if(attrib.equals("Att_Daily_val_int_per")){								 
								 valueElementMR17EU.setTextContent(lValue);
	    					   }else if(attrib.equals("Att_Dis_on_Pack")){	    						  
	    						   valueElementMR17EU.setAttribute("ID", lValue.equals("true") ? "Y" : "N");
	    					   }else if(attrib.equals("Att_Meas_prec_cod")){
	    						   valueElementMR17EU.setAttribute("ID", lValue);
	    					   }else{				
	    						   
						valueElementMR17EU.setTextContent(lValue);
	    					   }
						metaDataElementMWR17EU.appendChild(valueElementMR17EU);
						entyCrossRefElementMR17EU.appendChild(metaDataElementMWR17EU);					
						productElementEntity.appendChild(entyCrossRefElementMR17EU);
						}
					}
					}
					*/
					//JULY7---ATT_DIS_ON_PACK---START---MRAUTRES output logic
					//Rooban's Dev
					if (mr17Path.equals(Configpath) && category.equals("MRAUTRES")) {
						String nutrientIndex = "";
						if (value.contains("nutriments#") && value.contains("/nutrimentsAutres/")) {
							int startPos = value.indexOf("nutriments#") + "nutriments#".length();
							int endPos = value.indexOf("/nutrimentsAutres/");
							nutrientIndex = value.substring(startPos, endPos);
						}

						if (mr17Path.contains("code")) {
							mrautresCurrentCodeByIndex.put(nutrientIndex, lValue);

 					    metaDataElementMWR17EU = doc.createElement("MetaData");
							String attribObj = splitInputDataSlash(value);
							entyCrossRefElementMR17EU = doc.createElement("EntityCrossReference");
							entyCrossRefElementMR17EU.setAttribute("EntityID", lValue);
							entyCrossRefElementMR17EU.setAttribute("Type", "Ree_ServingSize_Nutrients");						
							productElement.appendChild(entyCrossRefElementMR17EU);
							m++;
						}

						//JULY7---ATT_DIS_ON_PACK---track current nutrient code by nutrient index
						if (m > 0) {
							if (!(mr17Path.contains("code"))) {

								/*
								 * Att_Dis_on_Pack is generated along with Att_Qty_cont below.
								 * Skip direct output for /onPack row to avoid duplicate Att_Dis_on_Pack.
								 */
								//JULY7---ATT_DIS_ON_PACK---skip direct onPack row; generate with Att_Qty_cont to avoid duplicates
								if (attrib.equals("Att_Dis_on_Pack")) {
									continue;
								}

								valueElementMR17EU = doc.createElement("Value");
								valueElementMR17EU.setAttribute("AttributeID", attrib);
								valueElementMR17EU.setAttribute("QualifierID", "AllCountries");

								if (attrib.equals("Att_Daily_val_int_per")) {
									valueElementMR17EU.setTextContent(lValue);
	    					   } else if (attrib.equals("Att_Meas_prec_cod")) {
	    						   valueElementMR17EU.setAttribute("ID", lValue);
	    					   } else {
									valueElementMR17EU.setTextContent(lValue);
	    					   }

								metaDataElementMWR17EU.appendChild(valueElementMR17EU);

								/*
								 * Requirement implementation:
								 * If Att_Qty_cont exists, always create Att_Dis_on_Pack.
								 * If SPHERE onPack is provided, use it. Otherwise default to Y.
								 */
								//JULY7---ATT_DIS_ON_PACK---create Att_Dis_on_Pack when Att_Qty_cont exists
								if (attrib.equals("Att_Qty_cont")) {
									
									Element grossValue = doc.createElement("Value");
									grossValue.setAttribute("AttributeID", "Att_Gross_value");
									grossValue.setTextContent(lValue);
									metaDataElementMWR17EU.appendChild(grossValue);
									
									
									String currentCode = mrautresCurrentCodeByIndex.get(nutrientIndex);
									String nutrientKey = nutrientIndex + "|" + currentCode;
									String onPackValue = mrautresOnPackMap.get(nutrientKey);

									Element disOnPackValue = doc.createElement("Value");
									disOnPackValue.setAttribute("AttributeID", "Att_Dis_on_Pack");
									disOnPackValue.setAttribute("QualifierID", "AllCountries");

									if (onPackValue != null && !onPackValue.trim().isEmpty()) {
										disOnPackValue.setAttribute("ID", onPackValue.equals("true") ? "Y" : "N");
									} else {
										disOnPackValue.setAttribute("ID", "Y");
									}

									metaDataElementMWR17EU.appendChild(disOnPackValue);
								}
								

								entyCrossRefElementMR17EU.appendChild(metaDataElementMWR17EU);					
								productElementEntity.appendChild(entyCrossRefElementMR17EU);
							}
						}
					}

					
					/*-------------------- Serving Size nutritionalClaimNutrientElement -------------------------*/
					if (mr17Path.equals(Configpath) && category.equals("SSNUTRIENT")) {
						if(mr17Path.contains("VariantnutritionalClaimNutrientElement")){							
							metaDataSSNutrient = doc.createElement("MetaData");
							 entyCrossRefElementSSNutrient = doc.createElement("EntityCrossReference");
							if(attrib.equals("Att_Nut_Clm_elt")){	
								entyCrossRefElementSSNutrient.setAttribute("EntityID", nutriant(lValue));
							}
							entyCrossRefElementSSNutrient.setAttribute("Type", "Ree_ServingSize_Nutrients");														
							productElementEntity.appendChild(entyCrossRefElementSSNutrient);							
							}
						if(attrib.equals("Att_Nut_Clm_Typ")){							
							nNc =lValue;
						}
						if(attrib.equals("Att_Nut_Clm_elt")){	
							valueElementVNClm = doc.createElement("Value");
							valueElementVNClm.setAttribute("AttributeID", attrib.trim());
							valueElementVNClm.setAttribute("QualifierID", "AllCountries");						
							valueElementVNClm.setAttribute("ID",lValue);
								valueElementVNType = doc.createElement("Value");
								valueElementVNType.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
								valueElementVNType.setAttribute("QualifierID", "AllCountries");						
								valueElementVNType.setAttribute("ID",nNc);									
								metaDataSSNutrient.appendChild(valueElementVNClm);
								metaDataSSNutrient.appendChild(valueElementVNType);
								entyCrossRefElementSSNutrient.appendChild(metaDataSSNutrient);
								productElementEntity.appendChild(entyCrossRefElementSSNutrient);
							}

					}
					
					
					/*----------------NonNutrientClaims start------------------------*/
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("NNCLAIMS")) {						
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element nonNutrientClaims=doc.createElement("EntityCrossReference");
						Element nNCMetaData = doc.createElement("MetaData");
						
						if(attrib.equals("Att_Nut_Clm_Typ")){
							nNc =lValue;
						}
//						System.out.println("lvalue"+nNc);
							
						if(attrib.equals("Att_Nut_Clm_elt")){							
							if(lValue.equals("LACTOSE")){
								lValue ="LACTOSE_CLM";
							}else if(lValue.equals("ADDITIVES")){
								lValue="ADDITIVES_CLM";
							}else if(lValue.equals("HONEY")){
								lValue="HONEY_CLM";
							}	
							
							nonNutrientClaims.setAttribute("EntityID", lValue);
							nonNutrientClaims.setAttribute("Type", "Rpe_NonNutrientClaims");
							Element nNConstant = doc.createElement("Value");
							nNConstant.setAttribute("AttributeID", "Att_Clm_Typ_Dis_on_Pack");
							nNConstant.setAttribute("QualifierID", "AllCountries");
							
							Element nNCValue = doc.createElement("Value");						
							nNCValue.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
							//nNCValue.setAttribute("QualifierID", "AllCountries");
							if(nNc!=null){
								 nNConstant.setAttribute("ID", "Y");
								 nNCMetaData.appendChild(nNConstant);
								 nNCValue.setAttribute("ID", nNc);						
								 nNCMetaData.appendChild(nNCValue);
								}
							
							nonNutrientClaims.appendChild(nNCMetaData);
							productElement.appendChild(nonNutrientClaims);
							 }
						
										
					}
					
					
				/*	----------------NonNutrientClaims end------------------------*/
				if (fullPath.equals(Configpath) && category.equals("MR1")) {
						String[] attribObj = splitInputData(attrib);
						Element entyCrossRefElement = doc.createElement("EntityCrossReference");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						entyCrossRefElement.setAttribute("EntityID", attribObj[0]);
						entyCrossRefElement.setAttribute("Type", "Rpe_Recipe_Allergen");
						productElement.appendChild(entyCrossRefElement);
						Element metaDataElement = doc.createElement("MetaData");
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attribObj[1]);
						valueElement.setAttribute("ID", lValue);
						metaDataElement.appendChild(valueElement);
						entyCrossRefElement.appendChild(metaDataElement);
					}
					String mrPath = null;
					mrPath = fullPath.replaceAll("#\\d+","");
					if ((mrPath.equals(Configpath)) && (category.equals("MRMicro")) || (category.equals("MRMicroLOV"))) {
						String[] indexObj = splitInputData(fullPath);
						mrPath = indexObj[0];
						if (mrPath.equals(Configpath)) {
							if (!(hsContainers.contains(productId))) {
								hsContainers.add(productId);
								multiDataContainerElement = doc.createElement("MultiDataContainer");
								multiDataContainerElement.setAttribute("Type",
										"Dct_Additional_Microbiological_Criteria");
								dataContainersElement.appendChild(multiDataContainerElement);
								productElement.appendChild(dataContainersElement);
							}
						}
					}
					String mrEuPath = null;
					mrEuPath = fullPath.replaceAll("#\\d+","");
					if ((mrEuPath.equals(Configpath)) &&(category.equals("MREUMicro")) || (category.equals("MREUMicroLOV"))) {
						String[] indexObj = splitInputData(fullPath);
						mrEuPath = indexObj[0];
						if (mrEuPath.equals(Configpath)) {
							if (!(hsContainersEU.contains(productId))) {
								hsContainersEU.add(productId);
								//Element dataContainersElement = doc.createElement("DataContainers");
								multiDataContainerElementEU = doc.createElement("MultiDataContainer");
								multiDataContainerElementEU.setAttribute("Type",
										"Dct_Microbio_Criteria");
								dataContainersElement.appendChild(multiDataContainerElementEU);
								productElement.appendChild(dataContainersElement);
							}
						}
					}
                     
					if (mrPath != null && mrPath.equals(Configpath) && category.equals("MRMicro")) {
						String[] indexObj = splitInputData(fullPath);
						String index = indexObj[1];
						if ((!hs.contains(index))) {
							hs.add(index);
							Element dataContainerElement = doc.createElement("DataContainer");
							multiDataContainerElement.appendChild(dataContainerElement);
							valuesMR2Element = doc.createElement("Values");
							dataContainerElement.appendChild(valuesMR2Element);
						}
						
						Element valueElement = doc.createElement("Value");	
						if(attrib.equals("Att_Adtt_mic_org_warn_val_bas") || attrib.equals("Att_Adtt_mic_org_val_bas")){							
		                  String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets
		                  String[] entries = descJson.split("},\\s*\\{");
		                  for (String entry1 : entries) {
		                    String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");		                 	
		                      String mValue = partsDesc[0].split(":\\s*")[1].trim();
		                      String mUnite = partsDesc[1].split(":\\s*")[1].trim();		                 
		                     valueElement.setAttribute("AttributeID",attrib);
		                     valueElement.setAttribute("UnitID", unityMeasure(mUnite));
		                     valueElement.setTextContent(mValue);		                  }
		                  valuesMR2Element.appendChild(valueElement);		                  		                 
						}else if(attrib.equals("Att_Adtt_mic_org_meth")){
							valueElement.setAttribute("AttributeID", attrib);
							valueElement.setAttribute("QualifierID", "AllCountries");
							String lv = getLookupValue(attrib, lValue);
							valueElement.setAttribute("ID", lv);
							valuesMR2Element.appendChild(valueElement);
						}
						else if(attrib.equals("Att_Adtt_mic_org_max_val_prec")) {
							valueElement.setAttribute("AttributeID", attrib);
							valueElement.setAttribute("ID",lValue);
							valuesMR2Element.appendChild(valueElement);
						}
						else if(attrib.equals("Att_Adtt_mic_org_max_val")){
							maxVal=lValue;
							maxAttribut=attrib;
						}else if(attrib.equals("Att_Adtt_mic_org_warn_val")){
							warnVal=lValue;
							warAttribut=attrib;
						}
						else if(attrib.equals("UnitCode")){							
							if(maxAttribut.equals("Att_Adtt_mic_org_max_val")){
							Element maxElement = doc.createElement("Value");
							maxElement.setAttribute("AttributeID", maxAttribut);
							maxElement.setAttribute("UnitID",unityMeasure(lValue));
							maxElement.setTextContent(maxVal);
							valuesMR2Element.appendChild(maxElement);
							}
							if(warAttribut!=null){
							if(warAttribut.equals("Att_Adtt_mic_org_warn_val")){
								valueElement.setAttribute("AttributeID", warAttribut);
								valueElement.setAttribute("UnitID", unityMeasure(lValue));
								valueElement.setTextContent(warnVal);
								valuesMR2Element.appendChild(valueElement);
								}
							}
							
						}
						
						else {											
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						String lv = getLookupValue(attrib, lValue);
						valueElement.setTextContent(lv);
						valuesMR2Element.appendChild(valueElement);
						}
					}
					if (mrPath != null && mrPath.equals(Configpath) && category.equals("MRMicroLOV")) {
						String[] indexObj = splitInputData(fullPath);
						String index = indexObj[1];
						if ((!hs.contains(index))) {
							hs.add(index);
							Element dataContainerElement = doc.createElement("DataContainer");
							multiDataContainerElement.appendChild(dataContainerElement);
							valuesMR2Element = doc.createElement("Values");
							dataContainerElement.appendChild(valuesMR2Element);
						}
						Element valueElement = doc.createElement("Value");
						
						valueElement.setAttribute("AttributeID", attrib);
						if(!attrib.equals("Att_Adtt_mic_org_cod")){
						valueElement.setAttribute("QualifierID", "AllCountries");
						}
						valueElement.setAttribute("ID", lValue);
						valuesMR2Element.appendChild(valueElement);
						
					}
					if (mrPath != null && mrPath.equals(Configpath) && category.equals("MREUMicro")) {				

						String[] indexObj = splitInputData(fullPath);
						String index = indexObj[1];
						if ((!hsEU.contains(index))) {
							hsEU.add(index);
							Element dataContainerElement = doc.createElement("DataContainer");
							//dataContainerElement.setAttribute("Type", "Dct_Microbio_Criteria");
							multiDataContainerElementEU.appendChild(dataContainerElement);
							valuesMR2Element = doc.createElement("Values");
							dataContainerElement.appendChild(valuesMR2Element);
						}
						Element valueElement = doc.createElement("Value");						
						if(attrib.equals("Att_EU_Reg_mic_org_val_bas")){							
		                  String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets
		              String[] entries = descJson.split("},\\s*\\{");
		                  for (String entry1 : entries) {
		                    String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");		                	
		                      String mValue = partsDesc[0].split(":\\s*")[1].trim();
		                      String mUnite = partsDesc[1].split(":\\s*")[1].trim();		                  
		                     valueElement.setAttribute("AttributeID",attrib);
		                     valueElement.setAttribute("UnitID", unityMeasure(mUnite));
		                     valueElement.setTextContent(mValue);
		                  }
		                  valuesMR2Element.appendChild(valueElement);
		                  		                 
						}
						else if(attrib.equals("Att_EU_Reg_mic_org_max_val")){
							mmaxVal=lValue;
							mmaxAttribut=attrib;							
						}else if(attrib.equals("Att_EU_Reg_mic_org_warn_val")){
							mwarnVal=lValue;
							mwarAttribut=attrib;
						}
						else if(attrib.equals("UnitCode")){								
							if(mmaxAttribut.equals("Att_EU_Reg_mic_org_max_val")){
							Element maxElement = doc.createElement("Value");							
							maxElement.setAttribute("AttributeID", mmaxAttribut);
							maxElement.setAttribute("UnitID",unityMeasure(lValue));
							maxElement.setTextContent(mmaxVal);
							valuesMR2Element.appendChild(maxElement);
							}
							if(mwarAttribut!=null){
							if(mwarAttribut.equals("Att_EU_Reg_mic_org_warn_val")){
								valueElement.setAttribute("AttributeID", mwarAttribut);
								valueElement.setAttribute("UnitID", unityMeasure(lValue));
								valueElement.setTextContent(mwarnVal);
								valuesMR2Element.appendChild(valueElement);
								}
							}
							
						}else{
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						String lv = getLookupValue(attrib, lValue);
						valueElement.setTextContent(lv);
						valuesMR2Element.appendChild(valueElement);
						}
					}
					if (mrPath != null && mrPath.equals(Configpath) && category.equals("MREUMicroLOV")) {
						String[] indexObj = splitInputData(fullPath);
						String index = indexObj[1];
						if ((!hsEU.contains(index))) {
							hsEU.add(index);
							Element dataContainerElement = doc.createElement("DataContainer");							
							multiDataContainerElementEU.appendChild(dataContainerElement);
							valuesMR2Element = doc.createElement("Values");
							dataContainerElement.appendChild(valuesMR2Element);
						}
						Element valueElement = doc.createElement("Value");						
						valueElement.setAttribute("AttributeID", attrib);
						if(!attrib.equals("Att_EU_Reg_mic_org_cod")){
						valueElement.setAttribute("QualifierID", "AllCountries");
						}
						if(attrib.equals("Att_EU_Reg_mic_crit_typ")){
							String lv = getLookupValue(attrib, lValue);
							valueElement.setTextContent(lv);
						}else{
						valueElement.setAttribute("ID", lValue);
						}
						valuesMR2Element.appendChild(valueElement);
					}
					 /*-----------------Start Att_catch-Fish_zone---------------------------*/
                    
                    try{
                   	 if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("FISHZONE")) {                  		 
                   		   							
 								Element cFish = doc.createElement("Value");
 								catchFish.setAttribute("AttributeID", attrib);
 								cFish.setAttribute("ID", lValue);
 								catchFish.appendChild(cFish);  	
 								valuesElement.appendChild(catchFish); 
                        		 }	          							
         							
                   	 }catch(NullPointerException e){
                       	 System.out.println("FISHZONE Error----------"+ErrorPath);
   						 System.out.println("FISHZONE Path----------"+e.getMessage());
   						 continue;
                        }
                    
           				if (fullPath.equals(Configpath) && category.equals("MR42")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						//System.out.println("inside");
						String lv = getLookupValue(attrib, product);
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lv);
						valueElements.get(productId).add(valueElement);
						valuesElement.appendChild(valueElement);
					}
				
					
	   /*--------------------------------end----------------------------------*/				
					
					if (fullPath.equals(Configpath) && category.equals("RECPV")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						mValueGroup.setAttribute("AttributeID", attrib);
						productElement.appendChild(mValueGroup);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);
						mValueGroup.appendChild(valueElement);
						valuesElement.appendChild(mValueGroup);
						valueElements.remove(productId);
					}
					
										
					if (fullPath.equals(Configpath) && category.equals("RECPQV")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						productElement.appendChild(valueElement);
						mValueGroup.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue);
						valueElements.get(productId).add(valueElement);
						mValueGroup.appendChild(valueElement);
						valuesElement.appendChild(mValueGroup);
						valueElements.remove(productId);
					}
					
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("RECPMTF")) {
					
						Element valueElement = doc.createElement("Value");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue.equals("true") ? "Y" : "N");
						valueElements.get(productId).add(valueElement);
						valuesElement.appendChild(valueElement);
						
					}
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("RECPTF")) {
					//	System.out.println("inside RECPTF "+attrib);
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(attrib.equals("Att_ATP_lim_Val")){						
							String limValue=lValue.replace("&lt;", "<&lt;/>");
							valueElement.setTextContent(limValue);
						}else{
						valueElement.setTextContent(lValue);
						}						
						valuesElement.appendChild(valueElement);
					}
					if (fullPath.equals(Configpath) && category.equals("RECP")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);
						valueElements.get(productId).add(valueElement);
						valuesElement.appendChild(valueElement);
					}
					if (fullPath.equals(Configpath) && category.equals("RECPAV")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lValue);
						valueElements.get(productId).add(valueElement);
						valuesElement.appendChild(valueElement);
						
					}					
					if (fullPath.equals(Configpath) && category.equals("RECPLV")) {
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valuesElement.appendChild(mValueGroup);
						mValueGroup.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);						
						valueElement.setAttribute("QualifierID", "AllCountries");						
						mValueGroup.appendChild(valueElement);
						valueElements.get(productId).add(valueElement);
						valueElements.remove(productId);
						
					}
					
					if (fullPath.equals(Configpath) && category.equals("RECPWQ")) {
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valuesElement.appendChild(mValueGroup);
						mValueGroup.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);						
						valueElement.setAttribute("QualifierID", "AllCountries");						
						mValueGroup.appendChild(valueElement);
						valueElements.get(productId).add(valueElement);
						valueElements.remove(productId);
						
					}
					
					if (fullPath.equals(Configpath) && category.equals("RECPM")) {						
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");						
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						
						valuesElement.appendChild(valueElement);
						valueElements.get(productId).add(valueElement);
						valueElements.remove(productId);
						
					}
					if ((fullPath.equals(Configpath) && category.equals("RECPL"))) {
                        Map<String,String> map=new HashMap<>();
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																					// surrounding
																			// brackets
					
						String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(lValue);
				        Element valueGroup = doc.createElement("ValueGroup");
						valueGroup.setAttribute("AttributeID", attrib);
				        while (matcher.find()) {
				            String language = matcher.group(1); // Extract the language
				          //  System.out.println("language------" + language);
				            String text = matcher.group(2);     // Extract the text
				         //   System.out.println("text------" + text);
	                           if(language.equals("fr")){					            	
	                        	   for(i=0;i<France.length;i++){	
						            		 Element france = doc.createElement("Value");					            		
						            		france.setAttribute("QualifierID", France[i]);
						            		france.setTextContent(text);
											valueGroup.appendChild(france);
						            	}
	                        	   map.put(language, text);
						            }
						            if(language.equals("de"))
						            	for(i=0;i<German.length;i++){					            	
						            	{	
						            		 Element german = doc.createElement("Value");					            		
						            		 german.setAttribute("QualifierID", German[i]);
						            		 german.setTextContent(text);
											valueGroup.appendChild(german);
						            	}
						            	map.put(language, text);
						            }
						            if(language.equals("ar")){	
						            		for(i=0;i<Arabic.length;i++){	
						            		 Element arabic = doc.createElement("Value");					            		
						            		 arabic.setAttribute("QualifierID", Arabic[i]);
						            		 arabic.setTextContent(text);
											valueGroup.appendChild(arabic);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("ch")){					            	
						            	
						            		for(i=0;i<Chinese.length;i++){	
						            		 Element chinese = doc.createElement("Value");					            		
						            		 chinese.setAttribute("QualifierID", Chinese[i]);
						            		 chinese.setTextContent(text);
											valueGroup.appendChild(chinese);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("en")){			
						            		for(i=0;i<English.length;i++){	
						            		 Element english = doc.createElement("Value");					            		
						            		 english.setAttribute("QualifierID", English[i]);
						            		 english.setTextContent(text);
											valueGroup.appendChild(english);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("es")){				
						            		for(i=0;i<Spanish.length;i++){	
						            		 Element spanish = doc.createElement("Value");					            		
						            		 spanish.setAttribute("QualifierID", Spanish[i]);
						            		 spanish.setTextContent(text);
											valueGroup.appendChild(spanish);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("it")){
						            		for(i=0;i<Italian.length;i++){	
						            		 Element italian = doc.createElement("Value");					            		
						            		 italian.setAttribute("QualifierID", Italian[i]);
						            		 italian.setTextContent(text);
											valueGroup.appendChild(italian);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("pt")){
						            		for(i=0;i<Portuguese.length;i++){	
						            		 Element portuguese = doc.createElement("Value");					            		
						            		 portuguese.setAttribute("QualifierID", Portuguese[i]);
						            		 portuguese.setTextContent(text);
											valueGroup.appendChild(portuguese);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("nl")){		
						            		for(i=0;i<Dutch.length;i++){	
						            		 Element dutch = doc.createElement("Value");					            		
						            		 dutch.setAttribute("QualifierID", Dutch[i]);
						            		 dutch.setTextContent(text);
											valueGroup.appendChild(dutch);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("el")){		
					            		for(i=0;i<Greek.length;i++){	
					            		 Element greek = doc.createElement("Value");					            		
					            		 greek.setAttribute("QualifierID", Greek[i]);
					            		 greek.setTextContent(text);
										valueGroup.appendChild(greek);
					            	}
					            		map.put(language, text);
					            }						            
						            if(language.equals("ro")){		
					            		for(i=0;i<Ro.length;i++){	
					            		 Element ro = doc.createElement("Value");					            		
					            		 ro.setAttribute("QualifierID", Ro[i]);
					            		 ro.setTextContent(text);
										valueGroup.appendChild(ro);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("ru")){		
					            		for(i=0;i<Ru.length;i++){	
					            		 Element ru = doc.createElement("Value");					            		
					            		 ru.setAttribute("QualifierID", Ru[i]);
					            		 ru.setTextContent(text);
										valueGroup.appendChild(ru);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("sr")){		
					            		for(i=0;i<Sr.length;i++){	
					            		 Element sr = doc.createElement("Value");					            		
					            		 sr.setAttribute("QualifierID", Sr[i]);
					            		 sr.setTextContent(text);
										valueGroup.appendChild(sr);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("sv")){		
					            		for(i=0;i<Sv.length;i++){	
					            		 Element sv = doc.createElement("Value");					            		
					            		 sv.setAttribute("QualifierID", Sv[i]);
					            		 sv.setTextContent(text);
										valueGroup.appendChild(sv);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("hr")){		
					            		for(i=0;i<Hr.length;i++){	
					            		 Element hr = doc.createElement("Value");					            		
					            		 hr.setAttribute("QualifierID", Hr[i]);
					            		 hr.setTextContent(text);
										valueGroup.appendChild(hr);
					            	}
					            		map.put(language, text);
					            }
						            if(language.equals("ps")){		
					            		for(i=0;i<Ps.length;i++){	
					            		 Element ps = doc.createElement("Value");					            		
					            		 ps.setAttribute("QualifierID", Ps[i]);
					            		 ps.setTextContent(text);
										valueGroup.appendChild(ps);
					            	}
					            		map.put(language, text);
					            }
						            if(language.equals("sw")){		
					            		for(i=0;i<Sw.length;i++){	
					            		 Element sw = doc.createElement("Value");					            		
					            		 sw.setAttribute("QualifierID", Sw[i]);
					            		 sw.setTextContent(text);
										valueGroup.appendChild(sw);
					            	}
					            		map.put(language, text);
					            }
						      if(!LanguageCountryCode(language).equals(" ")){
				            Element value3 = doc.createElement("Value");
							value3.setAttribute("QualifierID", LanguageCountryCode(language));
							value3.setTextContent(text);
							valueGroup.appendChild(value3);
							map.put(language, text);
						            }
						      
				        }
						valuesElement.appendChild(valueGroup);
						if(attrib.equals("Att_Ing_det_Name")){
							ingMetaData.appendChild(valueGroup);
							productElement.appendChild(ingredients);
						}
						descriptions.remove(productId);
						break;
					}
					
					if ((fullPath.equals(Configpath) && category.equals("DPT"))) {
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						//String descJson = lValue.substring(1, lValue.length() - 2); // Remove
						
							String regex = "\\{code\\s*:\\s*\"(.*?)\",\\s*valeur\\s*:\\s*\"(.*?)\"}";
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(lValue);
							
						//	Element Values = doc.createElement("Values");
							while (matcher.find()) {
								Element TMG = doc.createElement("Value");
								Element TESD = doc.createElement("Value");
								Element TESC = doc.createElement("Value");
							
							String language = matcher.group(1); // Extract the language							
							String text = matcher.group(2);     // Extract the text							
							if(language.equals("TESC")){
								TESC.setAttribute("AttributeID","Att_TESC" );								
								TESC.setTextContent(text.replace(",", "."));	
								valuesElement.appendChild(TESC);
							
							}
				           if(language.equals("TESD")){
								TESD.setAttribute("AttributeID","Att_TESD" );								
								TESD.setTextContent(text.replace(",", "."));						
								valuesElement.appendChild(TESD);
							}
				           if(language.equals("TMG")){
								TMG.setAttribute("AttributeID","Att_TMG" );								
								TMG.setTextContent(text.replace(",", "."));
								valuesElement.appendChild(TMG);							
							}							
							}
							

					}
					
					if ((fullPath.equals(Configpath) && category.equals("DPCC"))) {					
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}						
					     	Element Values = doc.createElement("Values");
					     	Element ValuesMV = doc.createElement("Values");
						    dCTMDCMD = doc.createElement("DataContainer");						    
							Element value3 = doc.createElement("Value");
							Element value4 = doc.createElement("Value");
							Element value5 = doc.createElement("Value");
							Element mGV = doc.createElement("Value");
							Element mGV1 = doc.createElement("Value");
							Element mGV2 = doc.createElement("Value");
							if(fullPath.contains("tauxSel/mini")){
							value3.setAttribute("AttributeID", "Att_Expression");							
							value3.setAttribute("ID","MIN_PERCENT");
							value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
							value5.setAttribute("ID","SEL");
							value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
							value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
							value4.setTextContent(lValue);							
							}
							else if(fullPath.contains("tauxSel/maxi")){
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","MAX_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","SEL");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);
								}
							else if(fullPath.contains("extraitSec/mini")){								
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","MIN_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","DREX");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);								
								}
							else if(fullPath.contains("extraitSec/maxi")){
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","MAX_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","DREX");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);								
								}
							else if(fullPath.contains("tauxMGTotMi")){
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","AVG_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","MGTOT");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);								
								}
							else if(fullPath.contains("grasSurSec")){
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","MIN_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","MGES");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);								
								}
							else if(fullPath.contains("fatPercentageInDryMatter")){
								value3.setAttribute("AttributeID", "Att_Expression");								
								if(!fatPercode.get(SCrecipeid).equals("NO")){
									value3.setAttribute("ID","MIN_PERCENT");
									}else{
										value3.setAttribute("ID","AVG_PERCENT");
									}
								//value3.setAttribute("ID","AVG_PERCENT");
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","MGES");
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(lValue);								
								}
							else{
							try{
								
							String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets
							String[] entries = descJson.split("},\\s*\\{");							
							for (String entry1 : entries) {
							String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");							
							String mValue = partsDesc[0].split(":\\s*")[1].trim();
							String mUnite = partsDesc[1].split(":\\s*")[1].trim();
							if(mValue.equals("MGB")){
								dCTMDCMD = doc.createElement("DataContainer");
								value3.setAttribute("AttributeID", "Att_Expression");							
								value3.setAttribute("ID","AVG_PERCENT");								
								value4.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								value4.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								value4.setTextContent(mUnite);
								value5.setAttribute("AttributeID", "Att_Phy_chem_crit");
								value5.setAttribute("ID","MGB");																 
							    Values.appendChild(value3);
							    Values.appendChild(value4);
							    Values.appendChild(value5);
							    dCTMDCMD.appendChild(Values);
							    dCTMDC.appendChild(dCTMDCMD);								
							    dataContainersElement.appendChild(dCTMDC);
							    productElement.appendChild(dataContainersElement);
							}
							if(mValue.equals("MGV")){
								dCTMDCMDMV = doc.createElement("DataContainer");
								mGV.setAttribute("AttributeID", "Att_Expression");							
								mGV.setAttribute("ID","AVG_PERCENT");								
								mGV1.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								mGV1.setAttribute("UnitID", "unece.unit.PERCENTAGE");
								mGV1.setTextContent(mUnite);
								mGV2.setAttribute("AttributeID", "Att_Phy_chem_crit");
								mGV2.setAttribute("ID","MGV");								
								ValuesMV.appendChild(mGV);
								ValuesMV.appendChild(mGV1);
								ValuesMV.appendChild(mGV2);
								dCTMDCMDMV.appendChild(ValuesMV);
								dCTMDC.appendChild(dCTMDCMDMV);								
								dataContainersElement.appendChild(dCTMDC);
								productElement.appendChild(dataContainersElement);
							}
							//PH---START
							if(mValue.equals("PH"))
							{
								String[] pHMinMaxValue=mUnite.split("-");
								Element PH = doc.createElement("Value");
								Element PH1 = doc.createElement("Value");
								Element PH2 = doc.createElement("Value");								
								Element mPH = doc.createElement("Value");
								Element mPH1 = doc.createElement("Value");
								Element mPH2 = doc.createElement("Value");
								dCTMDCMD = doc.createElement("DataContainer");
								Element ValuesHHMAX = doc.createElement("Values");
								Element ValuesHHMIN = doc.createElement("Values");
								PH.setAttribute("AttributeID", "Att_Expression");							
								PH.setAttribute("ID","VAL_MIN");
								PH1.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								PH1.setTextContent(pHMinMaxValue[0]);
								PH2.setAttribute("AttributeID", "Att_Phy_chem_crit");
								PH2.setAttribute("ID","PH");
								ValuesHHMAX.appendChild(PH);
								ValuesHHMAX.appendChild(PH1);
								ValuesHHMAX.appendChild(PH2);								
								dCTMDCMD.appendChild(ValuesHHMAX);
								dCTMDC.appendChild(dCTMDCMD);								
								dataContainersElement.appendChild(dCTMDC);
								productElement.appendChild(dataContainersElement);
								dCTMDCMDMV = doc.createElement("DataContainer");
								mPH.setAttribute("AttributeID", "Att_Expression");							
								mPH.setAttribute("ID","VAL_MAX");
								mPH1.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								mPH1.setTextContent(pHMinMaxValue[1]);
								mPH2.setAttribute("AttributeID", "Att_Phy_chem_crit");
								mPH2.setAttribute("ID","PH");
								ValuesHHMIN.appendChild(mPH);
								ValuesHHMIN.appendChild(mPH1);
								ValuesHHMIN.appendChild(mPH2);
								dCTMDCMDMV.appendChild(ValuesHHMIN);
								dCTMDC.appendChild(dCTMDCMDMV);								
								dataContainersElement.appendChild(dCTMDC);
								productElement.appendChild(dataContainersElement);
							}
							if(mValue.equals("HUMIDITE"))
							{
								System.out.println("mUnite------"+mUnite);
								String[] HUMIDITE=mUnite.split(",");
								Element H = doc.createElement("Value");
								Element H1 = doc.createElement("Value");
								Element H2 = doc.createElement("Value");								
								Element mH = doc.createElement("Value");
								Element mH1 = doc.createElement("Value");
								Element mH2 = doc.createElement("Value");
								dCTMDCMD = doc.createElement("DataContainer");
								Element ValuesHUMMAX = doc.createElement("Values");
								Element ValuesHUMMIN = doc.createElement("Values");
								H.setAttribute("AttributeID", "Att_Expression");							
								H.setAttribute("ID","VAL_MIN");
								H1.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								H1.setTextContent(HUMIDITE[0]);
								H2.setAttribute("AttributeID", "Att_Phy_chem_crit");
								H2.setAttribute("ID","HUMIDITE");
								ValuesHUMMAX.appendChild(H);
								ValuesHUMMAX.appendChild(H1);
								ValuesHUMMAX.appendChild(H2);								
								dCTMDCMD.appendChild(ValuesHUMMAX);
								dCTMDC.appendChild(dCTMDCMD);								
								dataContainersElement.appendChild(dCTMDC);
								productElement.appendChild(dataContainersElement);
								dCTMDCMDMV = doc.createElement("DataContainer");
								mH.setAttribute("AttributeID", "Att_Expression");							
								mH.setAttribute("ID","VAL_MAX");
								mH1.setAttribute("AttributeID", "Att_Val_Of_Phy_chem_crit");
								mH1.setTextContent(HUMIDITE[1]);
								mH2.setAttribute("AttributeID", "Att_Phy_chem_crit");
								mH2.setAttribute("ID","HUMIDITE");
								ValuesHUMMIN.appendChild(mH);
								ValuesHUMMIN.appendChild(mH1);
								ValuesHUMMIN.appendChild(mH2);
								dCTMDCMDMV.appendChild(ValuesHUMMIN);
								dCTMDC.appendChild(dCTMDCMDMV);								
								dataContainersElement.appendChild(dCTMDC);
								productElement.appendChild(dataContainersElement);
							}
							//PH--END
							}
							}catch(Exception e){
								System.out.println("Recipe Error Records----"+ErrorPath);
								continue;
							}
							}							
							Values.appendChild(value3);
							Values.appendChild(value4);
							Values.appendChild(value5);							
							dCTMDCMD.appendChild(Values);
							dCTMDC.appendChild(dCTMDCMD);								
							dataContainersElement.appendChild(dCTMDC);
							productElement.appendChild(dataContainersElement);
						descriptions.remove(productId);
						break;
					}
				}
			}
			
		//} //Unique receipe 
			}
		}catch(Exception e){
			System.out.println("Recipe Error Records----"+ErrorPath);
			System.out.println("Error Records----"+e.getMessage()+"-----"+e.getCause()+"-----"+e.getLocalizedMessage());		
			continue;
		}
		}
		// Packaging -------------------------------------------------Packing
		

		for (Entry<String, List<String[]>> entry : packagingMap.entrySet()) {
			String ErrorPath = null;
			
			try{
			String productId = entry.getKey();
			String packid=productId.substring(0, productId.length()-2);
		//	if(!uniquPackageID.contains(TradeItemPack.get(packid))){
				uniquPackageID.add(TradeItemPack.get(packid));
			if("GRA02".equals(grade.get(productId.substring(0, productId.length()-2)))){
				packagingID="SP"+productId.substring(0, productId.length()-4);
				PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));
			}else if("DTUL".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))){				
				packagingID ="SP"+packid.substring(2);
				PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));
			}
			/**------- Semi Product package condition --------**/
			else if("SFUC".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))){
				packagingID ="SP"+packid.substring(2);				
				PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));
			}else if("SFUL".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))){
				packagingID ="SP"+packid.substring(2);				
				PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));
			}
			else{
			packagingID= TradeItemPack.get(productId.substring(0, productId.length()-2));
			PackageTradName =ProdId.get(productId.substring(0, productId.length()-2));			
			}
			
			if(PackageTradName.equals("EA")){
				PackageTradName = "BA";
			}
			if(PackageTradName.equals("PK")){
				PackageTradName = "PA";
			}		
			String packagingTCode =packagingTypeCode.get(productId.substring(0, productId.length()-2));
			if((!"Unknown".equals(packagingID) && !packageValidation.get(packid).equals("NO")) || packagingID.contains("SP")){
			List<String[]> productDetails = entry.getValue();
			String UserTypeID = userTypeID(ProdId.get(productId));
		    Element productElement = doc.createElement("Product");
			Element packagevalues = doc.createElement("Values");
			Element subpackageElement = doc.createElement("Product");
			Element subpackagevaluesElement = doc.createElement("Values");		
			
			Element name = doc.createElement("Name");			
			productElement.appendChild(name);
			Element keyValue = doc.createElement("KeyValue");
			Element codPAckValueGroup = doc.createElement("ValueGroup");
			Element codPAckValue = doc.createElement("Value");
			productsElement.appendChild(productElement);
			
			productElement.setAttribute("UserTypeID", UserTypeID);
			if(!packagingTCode.equals("NON")){
			productElement.setAttribute("ParentID", "PPH_"+PackageTradName+"_"+packagingTCode);
			}else{
				productElement.setAttribute("ParentID", "PPH_"+PackageTradName+"_PUG");	
			}
			
			keyValue.setAttribute("KeyID", "Packaging_Key");
			keyValue.setTextContent(packagingID);			
			productElement.appendChild(keyValue);
			productElement.appendChild(packagevalues);
			Element AttPackagingID = doc.createElement("Value");
			AttPackagingID.setAttribute("AttributeID", "Att_Packaging_ID");
			AttPackagingID.setTextContent(packagingID);
			packagevalues.appendChild(AttPackagingID);
			productElement.appendChild(packagevalues);
			codPAckValueGroup.setAttribute("AttributeID", "Att_cod_Pack_car");
			codPAckValue.setAttribute("QualifierID", "AllCountries");
			codPAckValue.setTextContent(packagingID);
			codPAckValueGroup.appendChild(codPAckValue);			
			packagevalues.appendChild(codPAckValueGroup);
			Element sphere = doc.createElement("Value");
			sphere.setAttribute("AttributeID", "Att_Src_Sys");
			sphere.setTextContent("SPHERE");
			packagevalues.appendChild(sphere);			
			String SubPackagingID = productId.substring(0, productId.length()-2);
			packagingID= TradeItemPack.get(SubPackagingID);
				
			packagingID= TradeItemPack.get(SubPackagingID);
		
			Element subkeyValue = doc.createElement("KeyValue");
			Element parentValue = doc.createElement("ParentKeyValue");
			Element parentkeyValue = doc.createElement("Value");
			Element subpackagename = doc.createElement("Name");			
			subpackageElement.setAttribute("UserTypeID", "PackagingSubType");			
			subpackageElement.appendChild(subpackagename);
			subkeyValue.setAttribute("KeyID", "Packaging_ST_Key");
			subkeyValue.setTextContent(packagingID+"001");
			subpackageElement.appendChild(subkeyValue);
			parentValue.setAttribute("KeyID", "Packaging_Key");
			parentValue.setTextContent(packagingID);
			subpackageElement.appendChild(parentValue);
			parentkeyValue.setAttribute("AttributeID", "Att_Packaging_ST_ID");
			parentkeyValue.setTextContent(packagingID+"001");
			subpackagevaluesElement.appendChild(parentkeyValue);
			subpackageElement.appendChild(subpackagevaluesElement);
			Element prinPkg = doc.createElement("Value");
			prinPkg.setAttribute("AttributeID", "Att_Prin_Pkg_Code");
			prinPkg.setAttribute("ID", "Y");
			subpackagevaluesElement.appendChild(prinPkg);
			if(!"GRA02".equals(grade.get(productId.substring(0, productId.length()-2)))
					&& !"DTUL".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))
					&& !"SFUC".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))
					&& !"SFUL".equals(SCULMapping.get(productId.substring(0, productId.length()-2)))){
			productElement.appendChild(subpackageElement);
			}
			HashSet<String> hsPackCompContainers1 = new HashSet<>();
			HashSet<String> hsCompPackage = new HashSet<>();
			HashSet<String> hsPalPackage = new HashSet<>();
			HashSet<String> hsPackage = new HashSet<>();
			HashSet<String> hsPackContainers = new HashSet<>();			
			Element multiDataContainerElement=null;
			Element multiDataContainerElementC=null;
			Element DCdataContainerElement=null;
			Element valuesMR2Element=null;
			Element valuesMR2ElementDctCOM=null;
			Element pMulValues = doc.createElement("MultiValue");
			String packMat=null;
			String pm=null;
			String seqNO=null;
			int SNo=0;
			Map<String,String> ComSeqNo=new HashMap<>();
			for (String[] detail : productDetails) {
				String group = detail[0];
				String product = detail[1];
				String value = detail[2];
				String lValue = detail[3];
				String fullPath = group + "_" + product + "_" + value;			
				ErrorPath = group + "|"+packagingID+"|" + product + "|" + value+"|"+lValue;			
				if(fullPath.equals("Caracteristiques Emballages Spec_nom_nom")){
				
					name.setTextContent(lValue);
					subpackagename.setTextContent(lValue+"_001");
					Element packName = doc.createElement("Value");
					packName.setAttribute("AttributeID", "Att_Dis_Name_Pack_car");
					packName.setAttribute("QualifierID", "AllCountries");
					packName.setTextContent(lValue);
					packagevalues.appendChild(packName);
                }
				for (String[] values : att) {
					entity = values[0]; // prod/var/package
					category = values[1];
					Configpath = values[2];
					attrib = values[3];					
					String mrPath = null;					
					if (fullPath.equals(Configpath) && category.equals("PACKIQ")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						mValueGroup.setAttribute("AttributeID", attrib);
						productElement.appendChild(mValueGroup);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);
						mValueGroup.appendChild(valueElement);
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(mValueGroup);
							
						}else{
							packagevalues.appendChild(mValueGroup);
						}
						valueElements.remove(productId);
					}
					if (fullPath.equals(Configpath) && category.equals("PACKWQ")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						mValueGroup.setAttribute("AttributeID", attrib);
						productElement.appendChild(mValueGroup);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);
						mValueGroup.appendChild(valueElement);
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(mValueGroup);
							//subpackageElement.appendChild(mValueGroup);
						}else{
							packagevalues.appendChild(mValueGroup);
						}
						valueElements.remove(productId);
					}
					
/*-----------------PUOM-------------------------------------*/
	        		
	        		if(( fullPath.equals(Configpath) && category.equals("PUOM"))){
	        			// umo = null;
	              	   if (!descriptions.containsKey(productId)) {
	                      descriptions.put(productId, new LinkedHashMap<>());
	                  }
	              
	                  String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets
	                 // System.out.println(descJson);
	                  String[] entries = descJson.split("},\\s*\\{");
	                  for (String entry1 : entries) {
	                	  Element  umo = doc.createElement("Value");
	                    String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");
	                    if(!partsDesc[0].trim().equals("volume:")){
	                      String pvalues = partsDesc[0].split(":\\s*")[1].trim();
	                      String unit = partsDesc[1].split(":\\s*")[1].trim();
	                      
	                      umo.setAttribute("AttributeID",attrib);
	                      	umo.setAttribute("QualifierID", "AllCountries");
	                      	  if(attrib.equals("Att_Vol")){
	                      		umo.setAttribute("UnitID", "unece.unit.MTQ");  
	                      	  }else{
	                          umo.setAttribute("UnitID", unityMeasure(unit));
	                      	  }
	                          umo.setTextContent(pvalues);
	                      descriptions.get(productId).put(pvalues, unit);
	                      if(packagingSubType(attrib)){
		                	  subpackagevaluesElement.appendChild(umo);
							}else{                              
								packagevalues.appendChild(umo);
							}
	                    }
	                    
	                  }	 
	                  descriptions.remove(productId);
	                  
	              }
	/*-----------------------PUOM END------------------------------------------------*/
	              if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("PACK")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	System.out.println("PACK----------"+attrib);
						
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);
						valueElements.get(productId).add(valueElement);
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(valueElement);
						}else{
							packagevalues.appendChild(valueElement);
						}
					}

	              if ((fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("PLANGONLY"))) {
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(lValue);				        
				        Element LGroup = doc.createElement("ValueGroup");
				        LGroup.setAttribute("AttributeID", attrib);
				        Map<String,String> lan = new HashMap<>();				        
				        while (matcher.find()) {
				            String language = matcher.group(1); // Extract the language					            
				            String text = matcher.group(2);
				            Element Lvalue = doc.createElement("Value");								
							Lvalue.setAttribute("QualifierID", Language(language));
							Lvalue.setTextContent(text);
							LGroup.appendChild(Lvalue);
							lan.put(language, text);
				        }
				        if(!lan.containsKey("en")){	
                            if(lan.containsKey("fr")){
                          	  String fValue = lan.get("fr");
                          	 Element Lvalue = doc.createElement("Value");								
    							Lvalue.setAttribute("QualifierID", "en-US");
    							Lvalue.setTextContent(fValue);
    							LGroup.appendChild(Lvalue);
							}else{                                      
						for (Entry<String, String> lang : lan.entrySet()) {
						//	System.out.println("Inside ---------"+lang.getKey());
						if(!"fr".equals(lang.getKey())){
						Element Lvalue = doc.createElement("Value");								
						Lvalue.setAttribute("QualifierID", "en-US");
						Lvalue.setTextContent(lang.getValue());
						LGroup.appendChild(Lvalue);	
						break;
						}
						}	
						}
						}
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(LGroup);
						}else{
							packagevalues.appendChild(LGroup);
						}						
						descriptions.remove(productId);
						
						break;
					}

					if ((fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("PACKL"))) {

						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																					// surrounding
																					// brackets
						String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(lValue);
				        Element valueGroup = doc.createElement("ValueGroup");
						valueGroup.setAttribute("AttributeID", attrib);
				      
				        Map<String,String> map=new HashMap<>();
				        while (matcher.find()) {
				            String language = matcher.group(1); // Extract the language				            
				            String text = matcher.group(2);     // Extract the text	
				            if(language.equals("fr")){					            	
	                        	   for(int i=0;i<France.length;i++){	
						            		 Element france = doc.createElement("Value");					            		
						            		france.setAttribute("QualifierID", France[i]);
						            		france.setTextContent(text);
											valueGroup.appendChild(france);
						            	}
	                        	   map.put(language, text);
						            }
						            if(language.equals("de"))
						            	for(int i=0;i<German.length;i++){					            	
						            	{	
						            		 Element german = doc.createElement("Value");					            		
						            		 german.setAttribute("QualifierID", German[i]);
						            		 german.setTextContent(text);
											valueGroup.appendChild(german);
						            	}
						            	map.put(language, text);
						            }
						            if(language.equals("ar")){	
						            		for(int i=0;i<Arabic.length;i++){	
						            		 Element arabic = doc.createElement("Value");					            		
						            		 arabic.setAttribute("QualifierID", Arabic[i]);
						            		 arabic.setTextContent(text);
											valueGroup.appendChild(arabic);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("ch")){					            	
						            	
						            		for(int i=0;i<Chinese.length;i++){	
						            		 Element chinese = doc.createElement("Value");					            		
						            		 chinese.setAttribute("QualifierID", Chinese[i]);
						            		 chinese.setTextContent(text);
											valueGroup.appendChild(chinese);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("en")){			
						            		for(int i=0;i<English.length;i++){	
						            		 Element english = doc.createElement("Value");					            		
						            		 english.setAttribute("QualifierID", English[i]);
						            		 english.setTextContent(text);
											valueGroup.appendChild(english);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("es")){				
						            		for(int i=0;i<Spanish.length;i++){	
						            		 Element spanish = doc.createElement("Value");					            		
						            		 spanish.setAttribute("QualifierID", Spanish[i]);
						            		 spanish.setTextContent(text);
											valueGroup.appendChild(spanish);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("it")){
						            		for(int i=0;i<Italian.length;i++){	
						            		 Element italian = doc.createElement("Value");					            		
						            		 italian.setAttribute("QualifierID", Italian[i]);
						            		 italian.setTextContent(text);
											valueGroup.appendChild(italian);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("pt")){
						            		for(int i=0;i<Portuguese.length;i++){	
						            		 Element portuguese = doc.createElement("Value");					            		
						            		 portuguese.setAttribute("QualifierID", Portuguese[i]);
						            		 portuguese.setTextContent(text);
											valueGroup.appendChild(portuguese);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("nl")){		
						            		for(int i=0;i<Dutch.length;i++){	
						            		 Element dutch = doc.createElement("Value");					            		
						            		 dutch.setAttribute("QualifierID", Dutch[i]);
						            		 dutch.setTextContent(text);
											valueGroup.appendChild(dutch);
						            	}
						            		map.put(language, text);
						            }
						            if(language.equals("ro")){		
					            		for(int i=0;i<Ro.length;i++){	
					            		 Element ro = doc.createElement("Value");					            		
					            		 ro.setAttribute("QualifierID", Ro[i]);
					            		 ro.setTextContent(text);
										valueGroup.appendChild(ro);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("ru")){		
					            		for(int i=0;i<Ru.length;i++){	
					            		 Element ru = doc.createElement("Value");					            		
					            		 ru.setAttribute("QualifierID", Ru[i]);
					            		 ru.setTextContent(text);
										valueGroup.appendChild(ru);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("sr")){		
					            		for(int i=0;i<Sr.length;i++){	
					            		 Element sr = doc.createElement("Value");					            		
					            		 sr.setAttribute("QualifierID", Sr[i]);
					            		 sr.setTextContent(text);
										valueGroup.appendChild(sr);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("sv")){		
					            		for(int i=0;i<Sv.length;i++){	
					            		 Element sv = doc.createElement("Value");					            		
					            		 sv.setAttribute("QualifierID", Sv[i]);
					            		 sv.setTextContent(text);
										valueGroup.appendChild(sv);
					            	}
					            		map.put(language, text);
					            }	
						            if(language.equals("hr")){		
					            		for(int i=0;i<Hr.length;i++){	
					            		 Element hr = doc.createElement("Value");					            		
					            		 hr.setAttribute("QualifierID", Hr[i]);
					            		 hr.setTextContent(text);
										valueGroup.appendChild(hr);
					            	}
					            		map.put(language, text);
					            }if(language.equals("ps")){		
				            		for(int i=0;i<Ps.length;i++){	
					            		 Element ps = doc.createElement("Value");					            		
					            		 ps.setAttribute("QualifierID", Ps[i]);
					            		 ps.setTextContent(text);
										valueGroup.appendChild(ps);
					            	}
					            		map.put(language, text);
					            }
						            if(language.equals("sw")){		
					            		for(int i=0;i<Sw.length;i++){	
					            		 Element sw = doc.createElement("Value");					            		
					            		 sw.setAttribute("QualifierID", Sw[i]);
					            		 sw.setTextContent(text);
										valueGroup.appendChild(sw);
					            	}
					            		map.put(language, text);
					            }
						            
						           if(map.containsKey("fr")){		
							    		  Element languag= doc.createElement("Value");
							    		  languag.setAttribute("QualifierID", "AllCountries");						    		  
							    		  languag.setTextContent(map.get("fr"));
										valueGroup.appendChild(languag);
										//en="FR";
										
							    	  }					     
							    	  else if(map.containsKey("en")){	
							    		  Element languag1= doc.createElement("Value");
							    		  languag1.setAttribute("QualifierID", "AllCountries");						    		  
							    		  languag1.setTextContent(map.get("en"));
										valueGroup.appendChild(languag1);
										//break;
							    	  }	
							    	  else{
							    		  for(Entry<String,String> lanug : map.entrySet()){
							    		 Element languag2= doc.createElement("Value");
							    		  languag2.setAttribute("QualifierID", "AllCountries");						    		  
							    		  languag2.setTextContent(lanug.getValue());
										  valueGroup.appendChild(languag2);
							    		  break;
							    	  }
							    	  }

				        }
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(valueGroup);
						}else{
							packagevalues.appendChild(valueGroup);
						}						
						descriptions.remove(productId);
						
					}
					/*------------------------------MR56-------------------------------------------------*/
	        		if (fullPath.equals(Configpath) && category.equals("MR56")) {
						Element valueElement = doc.createElement("Value");
					//	Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	productElement.appendChild(mValueGroup);
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("UnitID", "unece.unit.KGM");
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue);
						valueElements.get(productId).add(valueElement);
						if(packagingSubType(attrib)){
							subpackagevaluesElement.appendChild(valueElement);
						}else{
							packagevalues.appendChild(valueElement);
						}
						valueElements.remove(productId);
					}
                 /*------------------------MR56 End--------------------------------------------------*/  
	        		mrPath = fullPath.replaceAll("#\\d+","");
				//	System.out.println(mrPath);
					
			
	        		if ((mrPath != null && mrPath.equals(Configpath) && (category.equals("DCT_UCPACK")))) {					
						mrPath = fullPath.replaceAll("#\\d+","");
						if (mrPath.equals(Configpath)) {
							if (!(hsPackContainers.contains(productId))) {
								hsPackContainers.add(productId);
								Element dataContainersElement = doc.createElement("DataContainers");
								multiDataContainerElement = doc.createElement("MultiDataContainer");
								multiDataContainerElement.setAttribute("Type", "Dct_PackagingMaterial");
								dataContainersElement.appendChild(multiDataContainerElement);
								subpackageElement.appendChild(dataContainersElement);
							}
						}
					}
				
             try{
					if (mrPath != null && mrPath.equals(Configpath) && category.equals("DCT_UCPACK")) {
						String[] indexObj = splitInputData(fullPath);
						String index = indexObj[1];						
						String seqNo =index.substring(0,1);
						index = index.replaceAll("[a-z]", "");						
						index = index.replaceAll("_", "");						
						if ((!hsCompPackage.contains(index.substring(0,1)))) {
							hsCompPackage.add(index);
							Element dataContainerElement = doc.createElement("DataContainer");
							multiDataContainerElement.appendChild(dataContainerElement);
							valuesMR2Element = doc.createElement("Values");
							dataContainerElement.appendChild(valuesMR2Element);
						}
						Element seq = doc.createElement("Value");
						Element valueElement = doc.createElement("Value");					
						valueElement.setAttribute("AttributeID", attrib);
						if(!hsPalPackage.contains(seqNo)){
							hsPalPackage.add(seqNo);
							SNo =Integer.parseInt(seqNo)+1;							
							seq.setAttribute("AttributeID", "Att_Pack_Mat_Seq");
							seq.setAttribute("ID", String.valueOf(SNo));
							valuesMR2Element.appendChild(seq);
							}
						if(attrib.equals("Att_Pack_Mat") ||attrib.equals("Att_Pack_Mat_Col_if") ||attrib.equals("Att_Pack_Mat_Cls")){
							valueElement.setAttribute("ID", lValue);
						}else if(attrib.equals("Att_Pack_Mat_Wgt")){							
							String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets
							String[] entries = descJson.split("},\\s*\\{");
							for (String entry1 : entries) {
							String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");		                 	
	                        String mValue = partsDesc[0].split(":\\s*")[1].trim();
	                       String mUnite = partsDesc[1].split(":\\s*")[1].trim();		                 
	                      
	                      valueElement.setAttribute("UnitID", unityMeasure(mUnite));
	                      valueElement.setTextContent(mValue);
							}
						}else if(attrib.equals("Att_Pck_raw_mat")){
							valueElement.setAttribute("QualifierID", "AllCountries");
							valueElement.setAttribute("ID", lValue);
						}else if(attrib.equals("Att_Pack_mat_elt")){
							//valueElement.setAttribute("QualifierID", "AllCountries");
							valueElement.setAttribute("ID", emballage_type(lValue));
						} 
						else{
						valueElement.setTextContent(lValue);
						}
						if(attrib.equals("Att_Pack_Mat")){
							if(lValue.equals("LAMINATED_CARTON")||lValue.equals("METAL_COMPOSITE")||lValue.equals("COMPOSITE") ){
							packMat =lValue;							
							seqNO=String.valueOf(SNo);							
							ComSeqNo.put(seqNO, packMat);							
							}
						}
						valuesMR2Element.appendChild(valueElement);
						
					}
					
             }catch(Exception e){
            	 System.out.println("DCT_UCPACK----- "+mrPath);
            	 System.out.println("DCT_UCPACK----- "+e.getMessage());
            	 continue;
             }            
             if ((mrPath != null && mrPath.equals(Configpath) &&(category.equals("DCT_COMP")))) {
					
				mrPath = fullPath.replaceAll("#\\d+","");
				if (mrPath.equals(Configpath)) {
					if (!(hsPackCompContainers1.contains(productId))) {
						hsPackCompContainers1.add(productId);
						Element dataContainersElement1 = doc.createElement("DataContainers");
						multiDataContainerElementC = doc.createElement("MultiDataContainer");
						multiDataContainerElementC.setAttribute("Type", "Dct_Composite_Packaging_Material");
						dataContainersElement1.appendChild(multiDataContainerElementC);
						subpackageElement.appendChild(dataContainersElement1);
					}
				}
			}
             
             try{
					if (mrPath != null && mrPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("DCT_COMP"))
					{
					String[] indexObj = splitInputData(fullPath);
					String firstIndex =fullPath.split("#")[1];
					firstIndex=firstIndex.substring(0,1);
					String secondIndex=fullPath.split("#")[2];
					secondIndex=secondIndex.substring(0,1);	
					String compIndex=firstIndex+secondIndex;					
					String index = indexObj[1];
					String compSeqNo =index.substring(0,1);					
					/*index = index.replaceAll("[a-z]", "");
					index = index.replaceAll("_", "");
					index = index.replaceAll("[A-Z]", "");*/				
					if ((!hsPackage.contains(compIndex))) {
						hsPackage.add(compIndex);
						DCdataContainerElement = doc.createElement("DataContainer");						
						valuesMR2ElementDctCOM = doc.createElement("Values");
						pm="YES";
					}

					Element valueElement = doc.createElement("Value");
					Element valueElementPM = doc.createElement("Value");
					Element valueElementSNo = doc.createElement("Value");
					if(attrib.equals("Att_Comp_Pack_Mat_Col") || attrib.equals("Att_Comp_Pack_Mat_Col")){
					valueElement.setAttribute("AttributeID", attrib);
					valueElement.setAttribute("ID", lValue);
				
					}else if(attrib.equals("Att_Comp_Pack_Mat_Thi")){
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lValue);
					}else if(attrib.equals("Att_Comp_Pck_raw_mat"))
					{
					valueElement.setAttribute("AttributeID", attrib);
					valueElement.setAttribute("ID", lValue);						
					}else if(attrib.equals("Att_Comp_Pack_raw_mat_cont_per"))
					{
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lValue);
					}else if(attrib.equals("Att_Comp_Pack_Mat_Wgt"))
					{
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("UnitID", "unece.unit.GRM");
						valueElement.setTextContent(lValue);
					}else if(attrib.equals("Att_Comp_Pack_Mat"))
					{
					valueElement.setAttribute("AttributeID", attrib);
					valueElement.setAttribute("ID", lValue);						
					}else if(attrib.equals("Att_Pack_Mat_Cls"))
					{
					valueElement.setAttribute("AttributeID", attrib);
					valueElement.setAttribute("ID", lValue);						
					}
					if(pm.equals("YES"))						
					{
					if(packMat !=null){
						int orderNo=Integer.parseInt(compSeqNo);
						orderNo =orderNo+1;
						String packmatValue=ComSeqNo.get(String.valueOf(seqNO));					
						valueElementSNo.setAttribute("AttributeID", "Att_Pack_Mat_Seq");
						valueElementSNo.setAttribute("ID", String.valueOf(seqNO));
						valuesMR2ElementDctCOM.appendChild(valueElementSNo);
						if(packmatValue != null){
						valueElementPM.setAttribute("AttributeID", "Att_Pack_Mat");
						valueElementPM.setAttribute("ID",packmatValue);
						valuesMR2ElementDctCOM.appendChild(valueElementPM);
						}
					pm="NO";
					}
					}					
					valuesMR2ElementDctCOM.appendChild(valueElement);					
					DCdataContainerElement.appendChild(valuesMR2ElementDctCOM);
					multiDataContainerElementC.appendChild(DCdataContainerElement);
					}
					
}
					catch(Exception e){						
						System.out.println("fullPath----- "+fullPath);
						System.out.println("Error path----- "+e.getMessage());
					
					}


					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("PMVALUE")) {
						//System.out.println("PMVALUE----- "+attrib);
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueGroup = doc.createElement("ValueGroup");
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						//valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setAttribute("ID",lValue);
						valueGroup.appendChild(valueElement);                      
						pMulValues.appendChild(valueGroup); 
						packagevalues.appendChild(pMulValues);
											}
				}
			}
	//	}  //unique package
			}
		}catch(Exception e){
			System.out.println("Packaging Error Records------"+ErrorPath);
			System.out.println("Error Message------"+e.getMessage());
		
			continue;
		}	
		}
	
	
		// Process each unique UC/UL Data
		for (Entry<String, List<String[]>> entry : dataMap.entrySet()) {
			String ErrorPath = null;
		try{
		String productId = entry.getKey();
		String pid=entry.getKey();
		Map<String, List<ApplicableMarket>> codeMap = new HashMap<>();
		Map<String,String> result=new HashMap<>();
		Map<String,String> ulresult=new HashMap<>();
		for (String id : dataMap.keySet()){
			String currentCountry="";
			List<String[]> lines= dataMap.get(productId);
			Map<String,String> tem = new HashMap<>();
			for(String line[] : lines ){
				String group =line[0];
				String path1 = line[1];
				String path2 =line[2];
				String value = line[3];		
				String path=path1+"_"+path2;				
				String status=path2;				
				if(path.equals("apo_code")){					
					if(!tem.isEmpty()){
						processCountrySites(tem,currentCountry,codeMap);
						tem.clear();
					}
					currentCountry=value;
				}else if(path2.contains("sitesExpe#")){
					String index =path2.split("/")[0];					
					String key = index +"/"+path2.substring(path2.indexOf('/')+1);				
					tem.put(key, value);
					processCountrySites(tem,currentCountry,codeMap);
				}				
				if((path1.equals("system")&& value.equals("true")) && !path2.equals("lastUpdateDate")){
				if("GDSStatus".equals(status)){
					result.put(productId, status);
					ulresult.put(productId, status);
				}else if(!result.containsKey(productId)){
					result.put(productId, status);
					ulresult.put(productId, status);
				}else{
					String existing =result.get(productId);
					if(comparePriority(status,existing) < 0){
						result.put(productId, status);
					}
					if(ulcomparePriority(status,existing) < 0){
						ulresult.put(productId, status);
					}
				}
				}
			}				
		}
		
		List<String[]> productDetails = entry.getValue();
		String UserTypeID = userTypeID(ProdId.get(productId));	
		Element name = doc.createElement("Name");
		Element variantName = doc.createElement("Name");
		Element productElement = doc.createElement("Product");
		Element variantElement = doc.createElement("Product");		
		Element valuesElement = doc.createElement("Values");		
		Element multiValueMR43 = doc.createElement("MultiValue");
		Element multiValue = doc.createElement("ValueGroup");	
	   Element variantValues = doc.createElement("Values");
		 Element constantElement = doc.createElement("Value");
          Element constantElement1 = doc.createElement("Value");
          Element constantElement2 = doc.createElement("Value");
          Element constantElement3 = doc.createElement("Value");
          Element constantElement4 = doc.createElement("Value");
          Element constantElement5 = doc.createElement("Value");
          Element constantElement6 = doc.createElement("Value");
          Element constantElement7 = doc.createElement("Value");
          Element constantElement13 = doc.createElement("Value");
          Element constantElement14 = doc.createElement("Value");
          Element constantElement15 = doc.createElement("Value");
          Element constantElement16 = doc.createElement("Value");        
          Element constantElement18 = doc.createElement("Value");
          Element Algconstant = doc.createElement("Value");
          Element varientElement1 = doc.createElement("Value");         
          Element varientElement3 = doc.createElement("Value");
          Element varientElement4 = doc.createElement("Value");
          Element varientElement5 = doc.createElement("Value");
          Element varientElement6 = doc.createElement("Value");
          Element varientElement7 = doc.createElement("Value");
          Element varientElement8 = doc.createElement("Value");         
          Element CommanConstant2 = doc.createElement("Value");
          Element varientElementSphere = doc.createElement("Value");
          Element prodcutKey = doc.createElement("Value");
          Element sphereID = doc.createElement("Value");
          Element VariantKey = doc.createElement("Value");
          Element classificationElement = doc.createElement("ClassificationReference");
          Element cmetaData = doc.createElement("MetaData");
          Element VarientServicSize = null;
          Element vSSmetaData = doc.createElement("MetaData");         
//          Element  RppVariantServingSizeId= doc.createElement("EntityCrossReference");        
//          Element  RppVariantServingSizeId2= doc.createElement("EntityCrossReference"); 
          Element  servingSizeId= doc.createElement("EntityCrossReference");
          String ConsumerUnite =pKCUnite.get(productId);
  		  String BaseUnite =pKBUnite.get(productId);
  		String SSIndex =null;  		  
		if (!UserTypeID.equals("Variant") && !UserTypeID.equals("")) {		
			PID=ProdId.get(productId);			
			if("GRA02".contentEquals(grade.get(productId)) && "EA".equals(PID)){				
				productElement.setAttribute("UserTypeID", UserTypeID+"_SC");
				}else{
				if(UserTypeID.equals("Savencia_Pack")&&((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){					
					productElement.setAttribute("UserTypeID", "Product");
				}else{
					productElement.setAttribute("UserTypeID", UserTypeID);
				}
			}			
			if (UserTypeID.equals("Savencia_Case")
					|| UserTypeID.equals("Savencia_Pallet") || UserTypeID.equals("Savencia_Display_Shipper")
					|| UserTypeID.equals("Savencia_Mixed_Module") || UserTypeID.equals("Savencia_Transport_Load")) {
			if(!BaseUnite.equals("false") && !ConsumerUnite.equals("true")){
				UserTypeID.equals("Savencia_Pack");
			}
				productElement.setAttribute("ParentID", "Trade_Item_Root");				

			} else if (UserTypeID.equals("Serving_Size")) {
				productElement.setAttribute("ParentID", "Nutrient_Root");
				
			} 
			productsElement.appendChild(productElement);
					}
		if (UserTypeID.equals("Product") || (BaseUnite.equals("false") && ConsumerUnite.equals("true"))) {	
		
			productElement.appendChild(name);
			Element vkeyvalue = doc.createElement("KeyValue");
			Element YODAKey = doc.createElement("KeyValue");
			Element parentKeyValue = doc.createElement("ParentKeyValue");
			if("GRA02".contentEquals(grade.get(productId))){
				variantElement.setAttribute("UserTypeID", "Product_Variant_SC");
			}else{			
			variantElement.setAttribute("UserTypeID", "Product_Variant");
			}
			variantElement.appendChild(variantName);			
			vkeyvalue.setAttribute("KeyID", "Variant_Key");
			vkeyvalue.setTextContent(productId+"001");
			variantElement.appendChild(vkeyvalue);
			parentKeyValue.setAttribute("KeyID", "YODA_Key");
			parentKeyValue.setTextContent(productId);			
			variantElement.appendChild(parentKeyValue);	
			productElement.appendChild(valuesElement);
			classificationElement.appendChild(cmetaData);
			Element rkeyvalue = doc.createElement("KeyValue");
			Element pkeyvalue = doc.createElement("KeyValue");	
			Element variantServingSizeKey = doc.createElement("KeyValue");
			Element variantServingSizeKey1 = doc.createElement("KeyValue");
			Element variantServingSizeKey2 = doc.createElement("KeyValue");
			Element ProductCrossReference1 = doc.createElement("ProductCrossReference");
			//Element ProductCrossReference2 = doc.createElement("ProductCrossReference");
			Element ProductCrossReference3 = doc.createElement("ProductCrossReference");
			
			 String  vRecipeID = RecipeID.get(productId+"10");
			try{ 
			if (recipeID != null) {
			 if(!vRecipeID.equals("Unknown")){
				ProductCrossReference1.setAttribute("Type", "Rpp_Variant_Recipe");					
			     variantElement.appendChild(ProductCrossReference1);
				rkeyvalue.setAttribute("KeyID", "Recipe_Key");
				rkeyvalue.setTextContent(vRecipeID);
				ProductCrossReference1.appendChild(rkeyvalue);
				variantElement.appendChild(ProductCrossReference1);
				}else{
					ProductCrossReference1.setAttribute("Type", "Rpp_Variant_Recipe");					
				     variantElement.appendChild(ProductCrossReference1);
					rkeyvalue.setAttribute("KeyID", "Recipe_Key");
					rkeyvalue.setTextContent("SR"+productId.substring(2));
					ProductCrossReference1.appendChild(rkeyvalue);
					variantElement.appendChild(ProductCrossReference1);
				}
			 if(!recipekey.isEmpty()){
				if(Objects.nonNull(servingsize.get(productId))){
					for (String key : recipekey.keySet()){
						if(productId.equals(key.substring(0, 8))){
//							Element  servingSizeId= doc.createElement("EntityCrossReference");
							 Element ServingSizeValue = doc.createElement("KeyValue");
							 Element SSmetaData = doc.createElement("MetaData");
							 servingSizeId.setAttribute("Type", "Rpe_Variant_Serving_Size");
							 ServingSizeValue.setAttribute("KeyID", "SS_Key");               
				RpeVariantServingSizeId = recipekey.get(key);
				String servingsizeValue=servingsize.get(productId);
				servingsizeValue=servingsizeValue.replace(",", ".");				
			//	if(servingsize.get(productId).substring(0,2).equals(RpeVariantServingSizeId.substring(RpeVariantServingSizeId.length()-2))){				
				if(servingsizeValue.substring(0,servingsizeValue.length()-2).trim().equals(recipeunity.get(productId).trim())){
					ServingSizeValue.setTextContent(RpeVariantServingSizeId);
				SSIndex="0";				
				servingSizeId.appendChild(ServingSizeValue);
				Element sSconstant = doc.createElement("Value");
				sSconstant.setAttribute("AttributeID", "Att_Num_Of_Serv_Per_Pack_Meas_Pre_cod");				
				sSconstant.setAttribute("QualifierID", "AllCountries");
				sSconstant.setAttribute("ID","APPROXIMATELY");	
				vSSmetaData.appendChild(sSconstant);
				servingSizeId.appendChild(vSSmetaData);				
				}
				variantElement.appendChild(servingSizeId);
				}
				}
				}
				
			 }
			 	/*if(recipekey.containsKey(productId+"100")){
					RppVariantServingSizeId2.setAttribute("Type", "Rpe_Variant_Serving_Size");
					Element sSmetaData = doc.createElement("MetaData");
					variantServingSizeKey2.setAttribute("KeyID", "SS_Key");               
					RpeVariantServingSizeId = recipekey.get(productId+"100");
					variantServingSizeKey2.setTextContent(RpeVariantServingSizeId);
					RppVariantServingSizeId2.appendChild(variantServingSizeKey2);
					Element sSconstant = doc.createElement("Value");
					sSconstant.setAttribute("AttributeID", "Att_Num_Of_Serv_Per_Pack_Meas_Pre_cod");
					sSconstant.setAttribute("QualifierID", "AllCountries");
					sSconstant.setAttribute("ID","APPROXIMATELY");
					sSmetaData.appendChild(sSconstant);
					Element rRSS = doc.createElement("Value");
					rRSS.setAttribute("AttributeID", "Att_Is_Recipe_SS");
					rRSS.setAttribute("ID","Y");
					sSmetaData.appendChild(rRSS);
					if(sSPrepar.containsKey(productId)){
						String sSValue = sSPrepar.get(productId);						
						Element sPP = doc.createElement("Value");
						sPP.setAttribute("AttributeID", "Att_Prepared_Food");
						sPP.setAttribute("ID",sSValue.equals("true") ? "Y" : "N");
						sSmetaData.appendChild(sPP);
					}
					RppVariantServingSizeId2.appendChild(sSmetaData);
					variantElement.appendChild(RppVariantServingSizeId2);
					}*/
			 	 if(!compositionIndex.isEmpty()){
					 for (String key : compositionIndex.keySet()){
						 if(productId.equals(key.substring(0, 8))){
						 Element  RppVariantServingSize= doc.createElement("EntityCrossReference");
						 Element variantServingSize = doc.createElement("KeyValue");
							RppVariantServingSize.setAttribute("Type", "Rpe_Variant_Serving_Size");
							Element sSmetaData = doc.createElement("MetaData");
							variantServingSize.setAttribute("KeyID", "SS_Key");					
						//	RpeVariantServingSizeId = compositionIndex.get(key);							
							variantServingSize.setTextContent(compositionIndex.get(key));
							RppVariantServingSize.appendChild(variantServingSize);
							Element sSconstant = doc.createElement("Value");
							sSconstant.setAttribute("AttributeID", "Att_Num_Of_Serv_Per_Pack_Meas_Pre_cod");
							sSconstant.setAttribute("QualifierID", "AllCountries");
							sSconstant.setAttribute("ID","APPROXIMATELY");
							sSmetaData.appendChild(sSconstant);
							Element rRSS = doc.createElement("Value");
							rRSS.setAttribute("AttributeID", "Att_Is_Recipe_SS");
							rRSS.setAttribute("ID","Y");
							sSmetaData.appendChild(rRSS);
							if(sSPrepar.containsKey(productId)){
								String sSValue = sSPrepar.get(productId);						
								Element sPP = doc.createElement("Value");
								sPP.setAttribute("AttributeID", "Att_Prepared_Food");
								sPP.setAttribute("ID",sSValue.equals("true") ? "Y" : "N");
								sSmetaData.appendChild(sPP);
							}
							RppVariantServingSize.appendChild(sSmetaData);
							variantElement.appendChild(RppVariantServingSize);								
							}					 
					 }
					 //compositionIndex.clear();
					 }
				
			}
			}catch(NullPointerException e){
				 System.out.println("Serving size----------"+ErrorPath);
				 continue;
				 
			 }			
			if (packagingID != null) {				
				packagingID= TradeItemPack.get(productId);
				ProductCrossReference3.setAttribute("Type", "Rpp_Variant_Pack");					
				variantElement.appendChild(ProductCrossReference3);
				pkeyvalue.setAttribute("KeyID", "Packaging_Key");
				if(!"GRA02".contentEquals(grade.get(productId)) && !"SFUC".equals(SCULMapping.get(productId))){
				pkeyvalue.setTextContent(packagingID);
				}else{
					//rooban'dev
					pkeyvalue.setTextContent("SP"+productId.substring(0, productId.length() - 2));	
				}
				ProductCrossReference3.appendChild(pkeyvalue);
				variantElement.appendChild(ProductCrossReference3);
				}
			
			YODAKey.setAttribute("KeyID","YODA_Key");
			YODAKey.setTextContent(productId);
			productElement.appendChild(YODAKey);			
			prodcutKey.setAttribute("AttributeID", "Att_YODA_Grp_cod");
			prodcutKey.setTextContent(productId);
			valuesElement.appendChild(prodcutKey);
			sphereID.setAttribute("AttributeID", "Att_Sphere_ID");
			sphereID.setTextContent(productId);
			valuesElement.appendChild(sphereID);
			VariantKey.setAttribute("AttributeID", "Att_Prd_Var_id");
			VariantKey.setTextContent(productId+"001");
			variantValues.appendChild(VariantKey);			
				if("GRA02".contentEquals(grade.get(productId)) && !"Unknown".equals(SCRefproduct.get(productId)) ){
					Element SC = doc.createElement("ProductCrossReference");
					Element SCkey = doc.createElement("KeyValue");						
					SC.setAttribute("Type", "First_Choice_Product");
					SCkey.setAttribute("KeyID", "YODA_Key");
					SCkey.setTextContent(SCRefproduct.get(productId));
					SC.appendChild(SCkey);
					productElement.appendChild(SC);					
				}
		}
		 if(UserTypeID.equals("Product")){ 
			 constantElement.setAttribute("AttributeID", "Att_add_Trd_itm_class_Prop_cod");
           	  constantElement.setTextContent("N");	           	  
       	  constantElement1.setAttribute("AttributeID", "Att_Cons_Sal_Cdt_cod");
       	  constantElement1.setTextContent("N");
       	  constantElement2.setAttribute("AttributeID", "Att_Dang_Goods_Reg_cod");
       	  constantElement2.setTextContent("ZNA");
       	  constantElement3.setAttribute("AttributeID", "Att_Is_Dang_Sub");
       	  constantElement3.setAttribute("ID","N");
       	  constantElement4.setAttribute("AttributeID", "Att_Is_Non_Sold_Trd_itm_Ret");
       	  constantElement4.setAttribute("ID","N");
       	  constantElement5.setAttribute("AttributeID", "Att_Is_Reg_For_Trpt");
       	  constantElement5.setAttribute("ID","N");
       	  constantElement6.setAttribute("AttributeID", "Att_Is_Trd_itm_A_Serv");
       	  constantElement6.setAttribute("ID","N");
       	  constantElement7.setAttribute("AttributeID", "Att_Ord_Qty_Multi");
       	  constantElement7.setTextContent("1");       	 
          constantElement13.setAttribute("AttributeID", "Att_Ord_Qty_Min");
          constantElement13.setTextContent("1");
          constantElement14.setAttribute("AttributeID", "Att_Src_Sys");
          constantElement14.setTextContent("SPHERE");         
          /** Att_Created_In replaced by Att_Managed_By **/
          constantElement18.setAttribute("AttributeID", "Att_Created_In");
          constantElement18.setTextContent("Sphere");
       	  valuesElement.appendChild(constantElement1);
       	  valuesElement.appendChild(constantElement2);
       	  valuesElement.appendChild(constantElement3);
       	  valuesElement.appendChild(constantElement4);
       	  valuesElement.appendChild(constantElement5);
       	  valuesElement.appendChild(constantElement6);
       	  valuesElement.appendChild(constantElement7);
       	  valuesElement.appendChild(constantElement13);
       	  valuesElement.appendChild(constantElement14);         
      	valuesElement.appendChild(constantElement18);     
       	  
             }
		       
           	  varientElement1.setAttribute("AttributeID", "Att_Duty_Fee_Cat_cod");
           	  varientElement1.setAttribute("ID","LOW");
           	  /*varientElement2.setAttribute("AttributeID", "Att_Ver_Cam_Ang_cod_For_pic");
           	  varientElement2.setTextContent("VERTICAL");
*/           varientElement3.setAttribute("AttributeID", "Att_Does_Trd_itm_Cont_Pes");
           	  varientElement3.setAttribute("ID","N");
           	  varientElement4.setAttribute("AttributeID", "Att_Is_Ing_Rel_Data_Prov");
           	  varientElement4.setAttribute("ID","Y");
           	  varientElement5.setAttribute("AttributeID", "Att_Man_prep_Typ_cod");
           	  varientElement5.setAttribute("ID","UNPREPARED");
           	  varientElement6.setAttribute("AttributeID", "Att_Nut_Rel_Data_Prov");
           	  varientElement6.setAttribute("ID","Y");
           	  varientElement7.setAttribute("AttributeID", "Att_prep_Sta_cod");
           	  varientElement7.setAttribute("ID","UNPREPARED");
           	varientElement8.setAttribute("AttributeID", "Att_Clm_mark_On_Pack");
           	varientElement8.setAttribute("QualifierID","AllCountries"); 
           	varientElement8.setAttribute("ID","Y");           
            Algconstant.setAttribute("AttributeID", "Att_Alg_Spe_Agcy");
  		  Algconstant.setTextContent("EU");
  		varientElementSphere.setAttribute("AttributeID", "Att_Src_Sys");
  		varientElementSphere.setTextContent("SPHERE");
           	variantValues.appendChild(varientElement1);
           //	variantValues.appendChild(varientElement2);
           	variantValues.appendChild(varientElement3);
           	variantValues.appendChild(varientElement4);
           	variantValues.appendChild(varientElement5);
           	variantValues.appendChild(varientElement6);
           	variantValues.appendChild(varientElement7);
           	variantValues.appendChild(varientElement8);
           	variantValues.appendChild(varientElementSphere);
           	//	variantValues.appendChild(CommanConstant);
          // 	variantValues.appendChild(CommanConstant1);
           	variantValues.appendChild(Algconstant);
		productElement.appendChild(valuesElement);

		Element kValue = doc.createElement("KeyValue");
		Element spValue = doc.createElement("KeyValue");
		Element TradeItemPackRefElement = doc.createElement("ProductCrossReference");
		Element TradeItemPackRefElement_pallet = doc.createElement("ProductCrossReference");
		Element TradeItemPackRefElement_dS = doc.createElement("ProductCrossReference");
		Element TradeItemPackRefElement_tL = doc.createElement("ProductCrossReference");
		Element TradeItemPackRefElement_pack = doc.createElement("ProductCrossReference");
		Element TradeItemPackRefElement_mM = doc.createElement("ProductCrossReference");		
		Element PromotionCrossReference = doc.createElement("ProductCrossReference");
		Element packageRefKV = doc.createElement("KeyValue");
		 Element imageValues = doc.createElement("MultiValue");
		 Element targetMarketCode = doc.createElement("MultiValue");
         Element tRASiteclassification = doc.createElement("ClassificationReference");
         Element cUTSiteclassification = doc.createElement("ClassificationReference");
         Element hSELSiteclassification = doc.createElement("ClassificationReference");               
         Element tRASitemetaData = doc.createElement("MetaData");
         Element cUTSitemetaData = doc.createElement("MetaData");         
     Element hSELSitemetaData = doc.createElement("MetaData");         		 
			tRASiteclassification.appendChild(tRASitemetaData);
			cUTSiteclassification.appendChild(cUTSitemetaData);				
			if(UserTypeID.equals("Savencia_Case")){			
				productElement.appendChild(name);
			Element packageRef = doc.createElement("ProductCrossReference");
			Element sSValue = doc.createElement("Value");
			Element cASEconstant = doc.createElement("Value");		
			Element SSsphereID = doc.createElement("Value");
			String 	sSpackagingID= TradeItemPack.get(productId);			
			kValue.setAttribute("KeyID","YODA_Key");
			kValue.setTextContent(productId);
			productElement.appendChild(kValue);
			sSValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
			sSValue.setTextContent(productId);
			valuesElement.appendChild(sSValue);
			SSsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
			SSsphereID.setTextContent(productId);
			valuesElement.appendChild(SSsphereID);
			cASEconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
			cASEconstant.setTextContent("H87");
	       	valuesElement.appendChild(cASEconstant);
	       	Element sCsphere = doc.createElement("Value");
	       	sCsphere.setAttribute("AttributeID", "Att_Src_Sys");
	       	sCsphere.setTextContent("SPHERE");
	       	valuesElement.appendChild(sCsphere);
	      	if(!sSpackagingID.equals("Unknown")){
			packageRef.setAttribute("Type", "Rpp_TradeItem_Pack");
			packageRefKV.setAttribute("KeyID", "Packaging_Key");
			packageRefKV.setTextContent(sSpackagingID);
			packageRef.appendChild(packageRefKV);
			productElement.appendChild(packageRef);
			}  
			/**--------Case Package implementation-----**/
			if("DTUL".equals(SCULMapping.get(productId)) || "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
				packageRef.setAttribute("Type", "Rpp_TradeItem_Pack");
				packageRefKV.setAttribute("KeyID", "Packaging_Key");
				packageRefKV.setTextContent("SP"+productId.substring(2));
				packageRef.appendChild(packageRefKV);
				productElement.appendChild(packageRef);
			}
			
		}
		if(UserTypeID.equals("Savencia_Pallet")){			
			productElement.appendChild(name);			
			String	SPalletID =SavenciaCaseID.get(productId);
			Set<String> sds= SDSmap.get(productId);
			Iterator<String> iterator = sds.iterator();
			Element sPname = doc.createElement("KeyValue");
			sPname.setAttribute("KeyID", "YODA_Key");
			//sPname.setTextContent(SPalletID+"001");
			sPname.setTextContent(productId);
			productElement.appendChild(sPname);			
			//productElement.appendChild(spValue);		
			String 	sPpackagingID= TradeItemPack.get(productId);
			Element sPpackageRef = doc.createElement("ProductCrossReference");
			Element sPpackageRefKV = doc.createElement("KeyValue");
		    
		    if(!SPalletID.equals("Unknown")){
		    	while(iterator.hasNext()){
					Element splletValue = doc.createElement("KeyValue");
					String refId = iterator.next();
		//	TradeItemPackRefElement_pallet.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");			
			String traditemcode1=ProdId.get(refId);
			if("EA".equals(traditemcode1)){
				splletValue.setAttribute("KeyID","Variant_Key");
				splletValue.setTextContent(refId+"001");
			}
			else{
				splletValue.setAttribute("KeyID","YODA_Key");
				splletValue.setTextContent(refId);
			}					
		    }
		    }
		 
			Element sPValue = doc.createElement("Value");
			Element SPsphereID = doc.createElement("Value");
			sPValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
			sPValue.setTextContent(productId);
			valuesElement.appendChild(sPValue);
			SPsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
			SPsphereID.setTextContent(productId);
			valuesElement.appendChild(SPsphereID);
			Element palletconstant = doc.createElement("Value");
			Element palletconstant_1 = doc.createElement("Value");
			palletconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
			palletconstant.setTextContent("H87");
	       	valuesElement.appendChild(palletconstant);
	       	palletconstant_1.setAttribute("AttributeID", "Att_Stack_Fact_Typ");
	       	palletconstant_1.setAttribute("ID","STORAGE_UNSPECIFIED");
	       	valuesElement.appendChild(palletconstant_1);
	       	Element spsphere = doc.createElement("Value");
	       	spsphere.setAttribute("AttributeID", "Att_Src_Sys");
	       	spsphere.setTextContent("SPHERE");
	       	valuesElement.appendChild(spsphere);

			if(!sPpackagingID.equals("Unknown")){
			sPpackageRef.setAttribute("Type", "Rpp_TradeItem_Pack");
			sPpackageRefKV.setAttribute("KeyID", "Packaging_Key");
			//sPpackageRefKV.setTextContent(sPpackagingID+"001");
			sPpackageRefKV.setTextContent(sPpackagingID);
			sPpackageRef.appendChild(sPpackageRefKV);
			productElement.appendChild(sPpackageRef);
		     }			
				if("DTUL".equals(SCULMapping.get(productId)) || "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
					sPpackageRef.setAttribute("Type", "Rpp_TradeItem_Pack");
					sPpackageRefKV.setAttribute("KeyID", "Packaging_Key");
					sPpackageRefKV.setTextContent("SP"+productId.substring(2));
					sPpackageRef.appendChild(sPpackageRefKV);
					productElement.appendChild(sPpackageRef);
				}
		}
		
		if(UserTypeID.equals("Savencia_Display_Shipper")){					
			productElement.appendChild(name);		
			String	displayShipperID =SavenciaCaseID.get(productId);
			Set<String> sds= SDSmap.get(productId);		
			Iterator<String> iterator = sds.iterator();		
			String traditemcode=ProdId.get(displayShipperID);
			Element sPname = doc.createElement("KeyValue");
			sPname.setAttribute("KeyID", "YODA_Key");			
			sPname.setTextContent(productId);			
			productElement.appendChild(sPname);		
			String 	sPpackagingID= TradeItemPack.get(productId);
			Element displayShiperRef = doc.createElement("ProductCrossReference");
			Element displayShiperRefKV = doc.createElement("KeyValue");
			
			if(!displayShipperID.equals("Unknown")){
				while(iterator.hasNext()){
					Element sdsValue = doc.createElement("KeyValue");
					String refId = iterator.next();
			//	TradeItemPackRefElement_dS.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");	
			//productElement.appendChild(TradeItemPackRefElement);
				String traditemcode1=ProdId.get(refId);
			if("EA".equals(traditemcode1)){
				sdsValue.setAttribute("KeyID","Variant_Key");
				sdsValue.setTextContent(refId+"001");
				
			}else{
				sdsValue.setAttribute("KeyID","YODA_Key");
				sdsValue.setTextContent(refId);
			}
			
		//	TradeItemPackRefElement_dS.appendChild(sdsValue);
			
			}
			}
			//productElement.appendChild(TradeItemPackRefElement_dS);
			Element sPValue = doc.createElement("Value");
			Element DSsphereID = doc.createElement("Value");
			sPValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
			sPValue.setTextContent(productId);
			valuesElement.appendChild(sPValue);
			DSsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
			DSsphereID.setTextContent(productId);
			valuesElement.appendChild(DSsphereID);
			Element DSconstant = doc.createElement("Value");
		//	Element DSconstant_1 = doc.createElement("Value");
			DSconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
			DSconstant.setTextContent("H87");
			valuesElement.appendChild(DSconstant);
			Element dSsphere = doc.createElement("Value");
	       	dSsphere.setAttribute("AttributeID", "Att_Src_Sys");
	       	dSsphere.setTextContent("SPHERE");
	       	valuesElement.appendChild(dSsphere);
			if(!sPpackagingID.equals("Unknown")){
			displayShiperRef.setAttribute("Type", "Rpp_TradeItem_Pack");
			displayShiperRefKV.setAttribute("KeyID", "Packaging_Key");
			//sPpackageRefKV.setTextContent(sPpackagingID+"001");
			displayShiperRefKV.setTextContent(sPpackagingID);
			displayShiperRef.appendChild(displayShiperRefKV);
			productElement.appendChild(displayShiperRef);	
			}
			// String displayName =SCDName.get(productId);				
				if("DTUL".equals(SCULMapping.get(productId)) || "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
					displayShiperRef.setAttribute("Type", "Rpp_TradeItem_Pack");
					displayShiperRefKV.setAttribute("KeyID", "Packaging_Key");
					displayShiperRefKV.setTextContent("SP"+productId.substring(2));
					displayShiperRef.appendChild(displayShiperRefKV);
					productElement.appendChild(displayShiperRef);
				}
			
			//productElement.appendChild(classificationElement);
		}
				
			if(UserTypeID.equals("Savencia_Transport_Load")){					
					productElement.appendChild(name);					
					String	transportLoadID =SavenciaCaseID.get(productId);	
					Set<String> sds= SDSmap.get(productId);
					Iterator<String> iterator = sds.iterator();				
					Element sPname = doc.createElement("KeyValue");
					sPname.setAttribute("KeyID", "YODA_Key");
					//sPname.setTextContent(SPalletID+"001");
					sPname.setTextContent(productId);
					productElement.appendChild(sPname);		
					String 	sPpackagingID= TradeItemPack.get(productId);
					Element displayShiperRef = doc.createElement("ProductCrossReference");
					Element displayShiperRefKV = doc.createElement("KeyValue");					
					if(!transportLoadID.equals("Unknown")){
						while(iterator.hasNext()){
							Element sTLValue = doc.createElement("KeyValue");
							String refId = iterator.next();
							String traditemcode1=ProdId.get(refId);
						TradeItemPackRefElement_tL.setAttribute("Type", "Rpp_TradeItemReference_TL");	
					//productElement.appendChild(TradeItemPackRefElement);

					if("EA".equals(traditemcode1)){
						sTLValue.setAttribute("KeyID","Variant_Key");
						sTLValue.setTextContent(refId+"001");
					}else{
						sTLValue.setAttribute("KeyID","YODA_Key");
						sTLValue.setTextContent(refId);
					}
					
				//	TradeItemPackRefElement_tL.appendChild(sTLValue);
						}
					}
				//	productElement.appendChild(TradeItemPackRefElement_tL);
					Element sPValue = doc.createElement("Value");
					Element TPsphereID = doc.createElement("Value");
					sPValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
					sPValue.setTextContent(productId);
					valuesElement.appendChild(sPValue);
					TPsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
					TPsphereID.setTextContent(productId);
					valuesElement.appendChild(TPsphereID);
					Element TLconstant = doc.createElement("Value");
				//	Element TLconstant_1 = doc.createElement("Value");
					TLconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
					TLconstant.setTextContent("H87");
					valuesElement.appendChild(TLconstant);
					Element tLsphere = doc.createElement("Value");
			       	tLsphere.setAttribute("AttributeID", "Att_Src_Sys");
			       	tLsphere.setTextContent("SPHERE");
			       	valuesElement.appendChild(tLsphere);

					if(!sPpackagingID.equals("Unknown")){
					displayShiperRef.setAttribute("Type", "Rpp_TradeItem_Pack");
					displayShiperRefKV.setAttribute("KeyID", "Packaging_Key");
					//sPpackageRefKV.setTextContent(sPpackagingID+"001");
					displayShiperRefKV.setTextContent(sPpackagingID);
					displayShiperRef.appendChild(displayShiperRefKV);
					productElement.appendChild(displayShiperRef);	
					}
					//String displayName =SCDName.get(productId);				
					if("DTUL".equals(SCULMapping.get(productId)) || "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
						displayShiperRef.setAttribute("Type", "Rpp_TradeItem_Pack");
						displayShiperRefKV.setAttribute("KeyID", "Packaging_Key");
						displayShiperRefKV.setTextContent("SP"+productId.substring(2));
						displayShiperRef.appendChild(displayShiperRefKV);
						productElement.appendChild(displayShiperRef);
					}
				}
			if((!BaseUnite.equals("false") && !ConsumerUnite.equals("true"))){
			if(UserTypeID.equals("Savencia_Pack")){
				//name.setTextContent("BALADE FOUETTE BE DOUX 130G X6X180P");			
				productElement.appendChild(name);				
				String	savenciaPackID =SavenciaCaseID.get(productId);				
				Set<String> sds= SDSmap.get(productId);
				Iterator<String> iterator = sds.iterator();
				Element sPname = doc.createElement("KeyValue");
				sPname.setAttribute("KeyID", "YODA_Key");
				//sPname.setTextContent(SPalletID+"001");
				sPname.setTextContent(productId);
				productElement.appendChild(sPname);
						
				String 	sPackagingID= TradeItemPack.get(productId);
				Element SavenciaPackRef = doc.createElement("ProductCrossReference");
				Element SavenciaPackKV = doc.createElement("KeyValue");
				
				if(!savenciaPackID.equals("Unknown")){
					while(iterator.hasNext()){
						Element sPValue = doc.createElement("KeyValue");
						String refId = iterator.next();
						String traditemcode1=ProdId.get(refId);
				//	TradeItemPackRefElement_pack.setAttribute("Type", "Rpp_TradeItemReference_PK");	
				//productElement.appendChild(TradeItemPackRefElement);
				if("EA".equals(traditemcode1)){
					sPValue.setAttribute("KeyID","Variant_Key");
					sPValue.setTextContent(savenciaPackID+"001");
				}else{
					sPValue.setAttribute("KeyID","YODA_Key");
					sPValue.setTextContent(savenciaPackID);
				}
			//	TradeItemPackRefElement_pack.appendChild(sPValue);
					}
				}
			//	productElement.appendChild(TradeItemPackRefElement_pack);
				Element sPValue = doc.createElement("Value");
				Element SPsphereID = doc.createElement("Value");
				sPValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
				sPValue.setTextContent(productId);
				valuesElement.appendChild(sPValue);
				SPsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
				SPsphereID.setTextContent(productId);
				valuesElement.appendChild(SPsphereID);
				Element packconstant = doc.createElement("Value");
			//	Element packconstant_1 = doc.createElement("Value");
				packconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
				packconstant.setTextContent("H87");
				valuesElement.appendChild(packconstant);
				Element sPsphere = doc.createElement("Value");
		       	sPsphere.setAttribute("AttributeID", "Att_Src_Sys");
		       	sPsphere.setTextContent("SPHERE");
		       	valuesElement.appendChild(sPsphere);

				if(!sPackagingID.equals("Unknown")){
				SavenciaPackRef.setAttribute("Type", "Rpp_TradeItem_Pack");
				SavenciaPackKV.setAttribute("KeyID", "Packaging_Key");
				//sPpackageRefKV.setTextContent(sPpackagingID+"001");
				SavenciaPackKV.setTextContent(sPackagingID);
				SavenciaPackRef.appendChild(SavenciaPackKV);
				productElement.appendChild(SavenciaPackRef);	
				}			
				if("DTUL".equals(SCULMapping.get(productId))|| "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
					SavenciaPackRef.setAttribute("Type", "Rpp_TradeItem_Pack");
					SavenciaPackKV.setAttribute("KeyID", "Packaging_Key");
					SavenciaPackKV.setTextContent("SP"+productId.substring(2));
					SavenciaPackRef.appendChild(SavenciaPackKV);
					productElement.appendChild(SavenciaPackRef);
				}
			}
			}
			if(UserTypeID.equals("Savencia_Mixed_Module")){
				//name.setTextContent("BALADE FOUETTE BE DOUX 130G X6X180P");			
				productElement.appendChild(name);				
				String	sMixedModuleID =SavenciaCaseID.get(productId);
				String traditemcode=ProdId.get(sMixedModuleID);
				Set<String> sds= SDSmap.get(productId);
				Iterator<String> iterator = sds.iterator();
				Element mMname = doc.createElement("KeyValue");
				mMname.setAttribute("KeyID", "YODA_Key");
				//sPname.setTextContent(SPalletID+"001");
				mMname.setTextContent(productId);
				
				productElement.appendChild(mMname);		
				String 	mixedModuleID= TradeItemPack.get(productId);
				Element mixedModuleRef = doc.createElement("ProductCrossReference");
				Element mixedModuleRefKV = doc.createElement("KeyValue");
				
				if(!sMixedModuleID.equals("Unknown")){
					while(iterator.hasNext()){
						Element sMMValue = doc.createElement("KeyValue");
						String refId = iterator.next();
				if("EA".equals(traditemcode)){
					sMMValue.setAttribute("KeyID","Variant_Key");
					sMMValue.setTextContent(refId+"001");
				}else{
					sMMValue.setAttribute("KeyID","YODA_Key");
					sMMValue.setTextContent(refId);
				}
					}
				}
				Element sPValue = doc.createElement("Value");
				Element MMsphereID = doc.createElement("Value");
				sPValue.setAttribute("AttributeID", "Att_YODA_Grp_cod");
				sPValue.setTextContent(productId);
				valuesElement.appendChild(sPValue);
				MMsphereID.setAttribute("AttributeID", "Att_Sphere_ID");
				MMsphereID.setTextContent(productId);
				valuesElement.appendChild(MMsphereID);
				Element MMconstant = doc.createElement("Value");			
				MMconstant.setAttribute("AttributeID", "Att_Num_Of_itm_In_Dpt_Of_car");
				MMconstant.setTextContent("H87");
				valuesElement.appendChild(MMconstant);
				Element mMsphere = doc.createElement("Value");
		       	mMsphere.setAttribute("AttributeID", "Att_Src_Sys");
		       	mMsphere.setTextContent("SPHERE");
		       	valuesElement.appendChild(mMsphere);

				if(!mixedModuleID.equals("Unknown")){
				mixedModuleRef.setAttribute("Type", "Rpp_TradeItem_Pack");
				mixedModuleRefKV.setAttribute("KeyID", "Packaging_Key");				
				mixedModuleRefKV.setTextContent(mixedModuleID);
				mixedModuleRef.appendChild(mixedModuleRefKV);
				productElement.appendChild(mixedModuleRef);	
				}			
				if("DTUL".equals(SCULMapping.get(productId)) || "SFUC".equals(SCULMapping.get(productId))|| "SFUL".equals(SCULMapping.get(productId))){
					mixedModuleRef.setAttribute("Type", "Rpp_TradeItem_Pack");
					mixedModuleRefKV.setAttribute("KeyID", "Packaging_Key");
					mixedModuleRefKV.setTextContent("SP"+productId.substring(2));
					mixedModuleRef.appendChild(mixedModuleRefKV);
					productElement.appendChild(mixedModuleRef);
				}
			}
		String proudctdisplayid= null;
		String RpcProductGTLink =null;
		Element entityCrosseRef = doc.createElement("EntityCrossReference");		
		Element netContent = null;
		Element netContentumo = null; 
		Element UlclassificationElement = doc.createElement("EntityCrossReference");
		Element YODAKEY = doc.createElement("KeyValue");
		Element Meta= doc.createElement("MetaData");
		Element CCHANNEL = doc.createElement("Value");		
		String ChannelValue=null;		
		Element tempEntityCrosseRef = null;
		boolean tempStatus=true;
		Element tempMetaData = doc.createElement("MetaData");
		Element rEPAICEntityCrosseRef =null;
		Element GDP = doc.createElement("Value");
		Element Loc_ID = doc.createElement("Value");
		Element LocIDValue = doc.createElement("Value");
		Element stockOwn = doc.createElement("Value");
		String cM = null;
		String marketN = null;
		Element address = doc.createElement("Value");
		Element contactName = doc.createElement("Value");
		String[] marketname={"BE","NL","DE","AT","CH"};		
		String Vpack=null;
		String Msiterole=null;
		String SellingValue= null;
		String MANvalue= null;
		int validation = 0;
		int lifstat = 0;
		Element flavouring = doc.createElement("MultiValue");		
		HashSet<String> applicablemarket = new HashSet<>();
		String localIDPL189=null;
		String localIDPL188=null;
		String localIDPL082=null;
		String localIDPL157=null;
		Element mDate= null;
		String nNc=null;
		Element animalCode = doc.createElement("MultiValue");
		Element  netUmo = doc.createElement("MultiValue");
		boolean valide=false;
			for (String[] detail : productDetails) {
				String group = detail[0];
				String product = detail[1];
				String value = detail[2];
				String lValue = detail[3];
				String fullPath = group + "_" + product + "_" + value;
				String gpcPah=group + "_" + product;
				ErrorPath = group + "|"+productId+"|" + product + "|" + value +"|"+lValue;				
				String cREFpath= group + "_" + product;
				for (String[] values : att) {
					entity = values[0]; // prod/var/package
					category = values[1];
					Configpath = values[2];
					attrib = values[3];
					if(group.equals("Fiches_Produits_Spec")&& value.equals("itemdisplayName")){						
						name.setTextContent(lValue);
						int count=lValue.lastIndexOf('-');
						if(count != -1){
							String before= lValue.substring(0,count+1);
							String after=lValue.substring(count+1);
							variantName.setTextContent(before+"001-"+after);
						}else{							 
						variantName.setTextContent(lValue+"-001");
						}
						 
						
	                }
				try{
					if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
					if(value.equals("categorieProduit")){					
						if(lValue.equals("CATP01")){
							proudctdisplayid="PRD_MASS_CONS";													
						}else if(lValue.equals("CATP02")){
							proudctdisplayid="PRD_INDUS_PRD";
						}else if(lValue.equals("CATP03")){
							proudctdisplayid="PRD_FOOD_SERV_SOL";
						}else if(lValue.equals("CATP04")){
							proudctdisplayid="PRD_FOOD_SERV_CAT";
						}else if(lValue.equals("CATP05")){
							proudctdisplayid="PRD_INT_INV_PRD";
						}						
					}
					
					if(value.equals("groupTechnology") && attrib.equals("Rpc_Product_GT_Link")){
					RpcProductGTLink=proudctdisplayid+pID(lValue);
						productElement.setAttribute("ParentID", RpcProductGTLink);
						}
					
					} }catch(NullPointerException e){
						 System.out.println("partentID----------"+ErrorPath);
						 continue;
						 
					 }
					try{
					if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
					if(gpcPah.equals(Configpath) && category.equals("GPC")){
						Element gPCclassificationElement = doc.createElement("ClassificationReference"); 						
						gPCclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
						gPCclassificationElement.setAttribute("Type", attrib); 	 						
	 						productElement.appendChild(gPCclassificationElement);
						
					}
					}}catch(NullPointerException e){
						 System.out.println("GPC----------"+ErrorPath);
						 continue;
						 
					 }
				
						if((value.equals("rearingCountry")) && (lValue.equals("FR"))){
						
						CommanConstant2.setAttribute("QualifierID","AllCountries"); 
		               	CommanConstant2.setTextContent("REARING");
		               	  variantElement.appendChild(CommanConstant2);
		               	
					}
						try{
					if(fullPath.equals(Configpath) && category.equals("netContent")){
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove														
								String[] entries = descJson.split("},\\s*\\{");
								for (String entry1 : entries) {
								netContent = doc.createElement("Value");
								String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");							
								String weight = partsDesc[0].split(":\\s*")[1].trim();
								String unit = partsDesc[1].split(":\\s*")[1].trim();
								netContent.setAttribute("AttributeID", attrib);
								if((unit.equals("CLT")) || (unit.equals("LT"))){
									netContent.setTextContent("LT");	
								}else{
									netContent.setTextContent("KGM");
								}
								variantValues.appendChild(netContent);
								
								}						
								} }catch(NullPointerException e){
	                              System.out.println("netcontent----------"+ErrorPath);
	                              continue;
	 
                             }
					//}
                   /*----------------------Constant added------------------------------*/  
                     if(value.equals("market") && (lValue.equals("DE") || lValue.equals("AT")||lValue.equals("CH") )){
                    	 constantElement15.setAttribute("AttributeID","Att_Is_Base_Price_Dec_Rel"); 
                    	 constantElement15.setAttribute("QualifierID","AllCountries");
                    	 constantElement15.setAttribute("ID", "Y");
                    	// constantElement15.setTextContent("True"); 	
                    	 valuesElement.appendChild(constantElement15);
                     }
                     if(value.equals("market") && lValue.equals("FR")){
                    	 constantElement16.setAttribute("AttributeID","Att_Min_Trd_itm_Lif_From_Time_Of_Prd"); 
                    	constantElement16.setTextContent("1"); 	
                    	valuesElement.appendChild(constantElement16);
                     }
                  /*------------------------MR50--------------------------*/   
                     if(value.equals("countryOfOrigin")) {
                    	 Element valueElement = doc.createElement("Value");
                    	 valueElement.setAttribute("AttributeID", "Att_Org_Prd_Place_Of_Far_cod");                    	
                    	 if(!lValue.equals(null) ) {
                    	 valueElement.setTextContent("EU_AGRICULTURE"); 
                    	 variantValues.appendChild(valueElement);
                     }else{
                    	 valueElement.setTextContent("FARMING_COUNTRY_OF_ORIGIN" );
                    	 variantValues.appendChild(valueElement);
                     }
                     }
                     
                     if(product.equals("Macro Article") && attrib.equals("Rpc_Macro_Article")){
                    	 Element gtclassificationElement = doc.createElement("ClassificationReference");
                    	 if(lValue.equals("00")){
                    		 lValue="0000000000";
                    	 }
							gtclassificationElement.setAttribute("ClassificationID", "CLS_"+ lValue);
							gtclassificationElement.setAttribute("Type", attrib);
							productElement.appendChild(gtclassificationElement);
                    	 
                     }
                     try{
					if (fullPath.equals(Configpath) && category.equals("CREF")) { 	
						
 						/*if(attrib.equals("Rpc_Market_Link")){
 							Element mclassificationElement = doc.createElement("ClassificationReference"); 						
 	 						mclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
 	 						mclassificationElement.setAttribute("Type", attrib); 	 						
 	 						variantElement.appendChild(mclassificationElement);
 						}*/
 						/*if(attrib.equals("Att_GPC_bri_cod")){
 							Element gclassificationElement = doc.createElement("ClassificationReference");
 							gclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
 							gclassificationElement.setAttribute("Type", attrib);
 							productElement.appendChild(gclassificationElement);
 	 						
 						}*/
 						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
 						if(attrib.equals("Rpc_Product_GT_Link")){
 							Element gtclassificationElement = doc.createElement("ClassificationReference");
 							gtclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
 							gtclassificationElement.setAttribute("Type", attrib);
 							productElement.appendChild(gtclassificationElement);
 						}
 						} 						
 						/*if(attrib.equals("Att_Tgt_mark")){
 							Element gtclassificationElement = doc.createElement("ClassificationReference");
 							gtclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
 							gtclassificationElement.setAttribute("Type", "Rpc_Market_Link");
 							productElement.appendChild(gtclassificationElement);
 	 						
 						}*/
 					} }catch(NullPointerException e){
						 System.out.println("CREF----------"+ErrorPath);
						 continue;
						 
					 }
                     
                     try{
     					if (fullPath.equals(Configpath) && category.equals("ULML")) {  

      						if(attrib.equals("Rpc_Market_Link")){
      						UlclassificationElement = doc.createElement("ClassificationReference");
      							/*if(lValue.equals("BEO")){
      								lValue="FR";
      							}*/
      							UlclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
      							UlclassificationElement.setAttribute("Type", attrib); 							
      							mDate = doc.createElement("MetaData");
      							Element crossValue = doc.createElement("Value");;
  								crossValue.setAttribute("AttributeID", "Att_is_approv_req");
  								crossValue.setAttribute("ID","Y");
      							mDate.appendChild(crossValue);
      							UlclassificationElement.appendChild(mDate);
  								variantElement.appendChild(UlclassificationElement); 
      						}
      						if(attrib.equals("Att_Start_Date")){     							
     							Element sDate = doc.createElement("Value");
     							sDate.setAttribute("AttributeID", attrib);
     							sDate.setAttribute("QualifierID", "AllCountries");
     							sDate.setTextContent(lValue.substring(0, 10));
     							mDate.appendChild(sDate);
     							UlclassificationElement.appendChild(mDate);
     							
     							}
      						if(attrib.equals("Att_End_Date")){     							
     							Element eDate = doc.createElement("Value");
     							eDate.setAttribute("AttributeID", attrib);
     							eDate.setAttribute("QualifierID", "AllCountries");
     							eDate.setTextContent(lValue.substring(0, 10));
     							mDate.appendChild(eDate);
     							UlclassificationElement.appendChild(mDate);
     							
     							}
      						productElement.appendChild(UlclassificationElement);
     					} }catch(NullPointerException e){
     						 System.out.println("ULML----------"+ErrorPath);
     						 continue;
     						 
     					 }
					/*---------------targetMarketInformation----------------- */
					try{
					if(fullPath.equals(Configpath) && category.equals("TMIN")){
					//	System.out.println("targetMarketInformation-----"+fullPath);
							Element gtclassificationElement = doc.createElement("ClassificationReference");
							/*if(lValue.equals("BEO")){
 								lValue="FR";
 							}*/
							gtclassificationElement.setAttribute("ClassificationID", "CLS_" + lValue);
							gtclassificationElement.setAttribute("Type", "Rpc_Market_Link");
							variantElement.appendChild(gtclassificationElement);
							Element crossValue = doc.createElement("Value");;
						    mDate = doc.createElement("MetaData");
							crossValue.setAttribute("AttributeID", "Att_is_approv_req");
							crossValue.setAttribute("ID","Y");
							mDate.appendChild(crossValue);
							gtclassificationElement.appendChild(mDate);
							variantElement.appendChild(gtclassificationElement); 
	 												}
					 }catch(NullPointerException e){
						 System.out.println("TMIN----------"+ErrorPath);
						 continue;						 
					 }
                     
					
                     try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("MANSITE")) {
						Element crossValue = doc.createElement("Value");			
						if(attrib.equals("Att_Prd_site")){
							if("PL100".equals(lValue)||"PL027".equals(lValue)||"PL005".equals(lValue)||"CH006".equals(lValue)){
							MANvalue=lValue+"XXX";
							}else{
								MANvalue=lValue;
							}					
							
						}
						
						if(attrib.equals("Att_Main_Prd_site_Req_field")){
							Element mSiteclassification = doc.createElement("ClassificationReference");
							Element pSitemetaData = doc.createElement("MetaData");							
							mSiteclassification.setAttribute("ClassificationID", "CLS_MAN_"+MANvalue);
							mSiteclassification.setAttribute("Type", "Rpc_Site_Role");
							crossValue.setAttribute("AttributeID", "Att_Main_Prd_site_Req_field");
							crossValue.setAttribute("ID",lValue.equals("true") ? "Y" : "N" );
							pSitemetaData.appendChild(crossValue);
							mSiteclassification.appendChild(pSitemetaData);
							
							Element mSiteclassification1 = doc.createElement("ClassificationReference");
                            mSiteclassification1.setAttribute("ClassificationID", "CLS_PAC_"+MANvalue);
                            mSiteclassification1.setAttribute("Type", "Rpc_Site_Role");
                            if (lValue.equals("true")){
                         	   Element pSitemetaData2 = doc.createElement("MetaData");   
                         	   Element crossValue2 = doc.createElement("Value");
                         	   crossValue2.setAttribute("AttributeID", "Att_Main_Pack_site");
    							  crossValue2.setAttribute("ID","Y");
   							pSitemetaData2.appendChild(crossValue2);
   							mSiteclassification1.appendChild(pSitemetaData2);
                            } 
                            
							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								variantElement.appendChild(mSiteclassification);
								variantElement.appendChild(mSiteclassification1);
							}else{
								productElement.appendChild(mSiteclassification);
							}
							
						}
						
					} }catch(NullPointerException e){
						 System.out.println("MANSITE Error----------"+ErrorPath);
						 System.out.println("MANSITE Error----------"+e.getMessage());
						 continue;
						 
					 }
                     try{
                    	 if(fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("HPACKSITE")) {
                    		 Element pAScrossValue = doc.createElement("Value");                    		 
                    		 Element pACSitemetaData = doc.createElement("MetaData");                    		 
     						if(attrib.equals("Rpc_Site_Role")){
     							if(Storage.contains(lValue) || lValue.equals("PL005") ){
     								Vpack=lValue+"XXX";
     							}else{
     							Vpack=lValue; 
     							}        						
     						}     						
     						if(attrib.equals("Att_Main_Pack_site")){
     							Element pACSiteclassification = doc.createElement("ClassificationReference");
     							pACSiteclassification.setAttribute("ClassificationID", "CLS_PAC_"+ Vpack);
        						pACSiteclassification.setAttribute("Type", "Rpc_Site_Role");
     							pAScrossValue.setAttribute("AttributeID", attrib);
     							pAScrossValue.setAttribute("ID",lValue.equals("true") ? "Y" : "N");
     							
    							pACSitemetaData.appendChild(pAScrossValue);
    							pACSiteclassification.appendChild(pACSitemetaData);
    							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
        							variantElement.appendChild(pACSiteclassification);    							
        						}else{    							
        							productElement.appendChild(pACSiteclassification);    							
        						}
     						}
     						
                    	 }
                    	 }catch(NullPointerException e){
                        	 System.out.println("Hierarchy PACKSITE Error----------"+ErrorPath);
    						 System.out.println("Hierarchy PACKSITE Error----------"+e.getMessage());
    						 continue;
                         }
 
                     
                     try{
                    	 if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("PACKSITE")) {
                    		 Element pAScrossValue = doc.createElement("Value");                    		 
                    		 Element pACSitemetaData = doc.createElement("MetaData");                    		 
                    		 if(attrib.equals("Att_Pack_site_cod")){
      							if(Storage.contains(lValue) || lValue.equals("PL005") ){
      								Vpack=lValue+"XXX";
      							}else{
      							Vpack=lValue; 
      							}
                    		 }
     						if(attrib.equals("Att_Main_Pack_site")){
     							Element pACSiteclassification = doc.createElement("ClassificationReference");
     							pACSiteclassification.setAttribute("ClassificationID", "CLS_PAC_"+ Vpack);
        						pACSiteclassification.setAttribute("Type", "Rpc_Site_Role");
     							pAScrossValue.setAttribute("AttributeID", attrib);
     							pAScrossValue.setAttribute("ID",lValue.equals("true") ? "Y" : "N");
     							
    							pACSitemetaData.appendChild(pAScrossValue);
    							pACSiteclassification.appendChild(pACSitemetaData);
    							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
        							variantElement.appendChild(pACSiteclassification);    							
        						}else{    							
        							productElement.appendChild(pACSiteclassification);    							
        						}
     						}
     						
                    	 }
                     }catch(NullPointerException e){
                    	 System.out.println("PACKSITE Error----------"+ErrorPath);
						 System.out.println("PACKSITE Error----------"+e.getMessage());
						 continue;
                     }
                 
                     try{
                    	 if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("SELSITE")) { 
                    		 if(group.contains("PL189")){
                    			 localIDPL189=lValue;                    			 
                    		 }
                    		 if(group.contains("PL157")){
                    			 localIDPL157=lValue;                    			 
                    		 }
                    		 if(group.contains("PL188")){
                    			 localIDPL188=lValue;                    			 
                    		 }
                    		 if(group.contains("PL082")){
                    			 localIDPL082=lValue;                    			 
                    		 }
                    		 if(attrib.equals("SELLING")){                         		
                    			 hSELSiteclassification = doc.createElement("ClassificationReference");
                    			 hSELSitemetaData = doc.createElement("MetaData");
                    			 GDP = doc.createElement("Value");
                    			 Loc_ID=doc.createElement("MultiValue");
          						stockOwn = doc.createElement("Value");          					
          						if(siterole.getProperty(lValue) != null){
          						hSELSiteclassification.setAttribute("ClassificationID", "CLS_SEL_"+ siterole.getProperty(lValue));
          						}else{          							
          							hSELSiteclassification.setAttribute("ClassificationID", lValue.equals("AMALT01") ? "CLS_SEL_"+lValue+"XXX" :"CLS_SEL_"+lValue);
          						}
          						hSELSiteclassification.setAttribute("Type", "Rpc_Site_Role");      						
          						
             					if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
             					
        							variantElement.appendChild(hSELSiteclassification);    							
        						}else{    			
        						
        							productElement.appendChild(hSELSiteclassification);    							
        						}
                         		 }
                    		  if(attrib.equals("Att_Stock_Own")){          							
       							        stockOwn.setAttribute("AttributeID", attrib);							
       									stockOwn.setAttribute("ID", "Y");
       							
       								hSELSitemetaData.appendChild(stockOwn);
       								hSELSiteclassification.appendChild(hSELSitemetaData);
       								
       								}
          						if(attrib.equals("Att_GDS_pub")){          							
          							GDP.setAttribute("AttributeID", attrib);
          							GDP.setAttribute("QualifierID", "AllCountries");
          							GDP.setAttribute("ID",lValue.equals("true") ? "Y" : "N" );
          							hSELSitemetaData.appendChild(GDP);
         							hSELSiteclassification.appendChild(hSELSitemetaData);
         						}
          						if(value.contains("codeGroupe") && lValue.equals("PL189")){          							
          							LocIDValue = doc.createElement("Value");
          							Loc_ID.setAttribute("AttributeID", "Att_Loc_ID");
          							LocIDValue.setTextContent(localIDPL189);
          							Loc_ID.appendChild(LocIDValue);
          							hSELSitemetaData.appendChild(Loc_ID);
         							hSELSiteclassification.appendChild(hSELSitemetaData);
          						}
          						if(value.contains("codeGroupe") && lValue.equals("PL157")){          							
          							LocIDValue = doc.createElement("Value");
          							Loc_ID.setAttribute("AttributeID", "Att_Loc_ID");
          							LocIDValue.setTextContent(localIDPL157);
          							Loc_ID.appendChild(LocIDValue);
          							hSELSitemetaData.appendChild(Loc_ID);
         							hSELSiteclassification.appendChild(hSELSitemetaData);
          						}
          						if(value.contains("codeGroupe") && lValue.equals("PL188")){          							
          							LocIDValue = doc.createElement("Value");
          							Loc_ID.setAttribute("AttributeID", "Att_Loc_ID");
          							LocIDValue.setTextContent(localIDPL188);
          							Loc_ID.appendChild(LocIDValue);
          							hSELSitemetaData.appendChild(Loc_ID);
         							hSELSiteclassification.appendChild(hSELSitemetaData);
          						}if(value.contains("codeGroupe") && lValue.equals("PL082")){          							
          							LocIDValue = doc.createElement("Value");
          							Loc_ID.setAttribute("AttributeID", "Att_Loc_ID");
          							LocIDValue.setTextContent(localIDPL082);
          							Loc_ID.appendChild(LocIDValue);
          							hSELSitemetaData.appendChild(Loc_ID);
         							hSELSiteclassification.appendChild(hSELSitemetaData);
          						}
          								
          							}
          							
                    	 }catch(NullPointerException | IndexOutOfBoundsException e){
                        	 System.out.println("Hierarchy SELLING SITE Error----------"+ErrorPath);
    						 System.out.println("Hierarchy SELLING Path----------"+e.getMessage());
    						 continue;
                         }

                     try{
                    	 if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("HSELSITE")) {
                    		 if(attrib.equals("Rpc_Site_Role")){                         		
                    			 hSELSiteclassification = doc.createElement("ClassificationReference");                    		
                    			 if(siterole.getProperty(lValue) != null){
               						hSELSiteclassification.setAttribute("ClassificationID", "CLS_SEL_"+ siterole.getProperty(lValue));
               						}else{
               							hSELSiteclassification.setAttribute("ClassificationID", "CLS_SEL_"+lValue);
               						}
          						hSELSiteclassification.setAttribute("Type", "Rpc_Site_Role");      						
          						
             					if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
             				      variantElement.appendChild(hSELSiteclassification);    							
        						}else{
        							productElement.appendChild(hSELSiteclassification);    							
        						}
                         		 }	
          							}
          							
                    	 }catch(NullPointerException | IndexOutOfBoundsException e){
                        	 System.out.println("Hierarchy SELLING SITE Error----------"+ErrorPath);
    						 System.out.println("Hierarchy SELLING Path----------"+e.getMessage());
    						 continue;
                         }
                    
                     
                     try{
                    	// if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("STOSITE")){
                    	 if(validation ==0){                    		 
                    		 for(String sitecode : codeMap.keySet()){                    		
 							Element classifcation = doc.createElement("ClassificationReference");
 							if(Storage.contains(sitecode)){ 	                    		
 	                    		classifcation.setAttribute("ClassificationID", "CLS_STO_"+ sitecode+"XXX");
 	                    		}else{
 	                    			classifcation.setAttribute("ClassificationID", "CLS_STO_"+sitecode);	
 	                    		}
 							
 							classifcation.setAttribute("Type", "Rpc_Site_Role");
 							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
  								variantElement.appendChild(classifcation);
  							}else{
  								productElement.appendChild(classifcation);
  								
  							}
 							Element meta = doc.createElement("MetaData");
 							Set<String> marketSet = new HashSet<>();
 							Map<String,String> cDate = new HashMap<>(); 
 							classifcation.appendChild(meta);
 							for(ApplicableMarket am : codeMap.get(sitecode)){ 								 								
 								if(!applicablemarket.contains(sitecode+am.country+am.date+am.age)){
 									applicablemarket.add(sitecode+am.country+am.date+am.age); 							
 								if(am.date != null){
 									Element ContractDate = doc.createElement("Value");
 									ContractDate.setAttribute("AttributeID", "Att_Ctr_Date");
 									ContractDate.setAttribute("QualifierID", am.country.equals("PC")? "FR" : am.country);
 									ContractDate.setTextContent(am.date);
 									meta.appendChild(ContractDate);
 								}
 								if(am.age != null){ 								
 									Element age = doc.createElement("Value");
 									age.setAttribute("AttributeID", "Att_Max_age_Desp_site_dep");
 									age.setAttribute("QualifierID", am.country.equals("PC")? "FR" : am.country);
 									age.setTextContent(am.age);
 									meta.appendChild(age);
 								}
 							}
 								cDate.put(am.country, am.date); 								
 								marketSet.add(am.country);
                    		}
 							for (Entry<String, String> tr : cDate.entrySet()){ 								
 								   Element ContractDate = doc.createElement("Value");
 								   if(tr.getValue() == null){
									ContractDate.setAttribute("AttributeID", "Att_Ctr_Date");
									ContractDate.setAttribute("QualifierID", tr.getKey().equals("PC")? "FR" : tr.getKey());
									ContractDate.setTextContent("00");
									meta.appendChild(ContractDate);
 								   }
 							}
 							Element multiMarket = doc.createElement("MultiValue");
 							multiMarket.setAttribute("AttributeID", "Att_Applicable_Markets");
 							for(String country : marketSet){
 								Element mMarket = doc.createElement("Value");
 								mMarket.setAttribute("ID", country.equals("PC")? "FR" : country);
 								multiMarket.appendChild(mMarket); 								
 							}
 							meta.appendChild(multiMarket);
 						}
                    		 validation=1;
                    	 }
 						
 					}catch(NullPointerException e){
 						 System.out.println("partentID----------"+ErrorPath);
 						 continue;
 						 
 					 }
					try{
					 if (fullPath.equals(Configpath) && category.equals("BSITE")) {						
	 						Element bSiteclassification = doc.createElement("ClassificationReference");
	 						/*if("BAKO01".equals(lValue) || "EVEN01".equals(lValue) || "HOPE01".equals(lValue)|| "KRAL03".equals(lValue)|| "FERRA01".equals(lValue)){
	 							bSiteclassification.setAttribute("ClassificationID","CLS_BR_PB_"+lValue);
	 						}*/
	 						if(l3.contains(lValue)){
	 							bSiteclassification.setAttribute("ClassificationID","CLS_"+lValue+"_L3");
	 						}
	 						else{
	 						bSiteclassification.setAttribute("ClassificationID","CLS_"+lValue);
	 						}
	 						bSiteclassification.setAttribute("Type", "Rpc_Product_Brand");	 						
//	 						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
	 						productElement.appendChild(bSiteclassification);
//	 						}
	 						
	 					}
					 }catch(NullPointerException e){
						 System.out.println("BSITE----------"+ErrorPath);
						 continue;						 
					 }
					 try{
					 if (fullPath.equals(Configpath) && category.equals("CSITE")) {
	 						Element cSiteclassification = doc.createElement("ClassificationReference");
	 						cSiteclassification.setAttribute("ClassificationID","CLS_CUT_"+lValue);
	 						cSiteclassification.setAttribute("Type", "Rpc_Site_Role");
	 						productElement.appendChild(cSiteclassification);
	 						
	 					}
					 }catch(NullPointerException e){
						 System.out.println("CSITE----------"+ErrorPath);
						 continue;						 
					 }
					 try{
					 if (fullPath.equals(Configpath) && category.equals("CCT")) { 						
						 if(attrib.equals("Att_Cus_Nom_cod")){ 						
 						Element RpeCustomClassificationType= doc.createElement("EntityCrossReference");
 						Element cCCMetaData = doc.createElement("MetaData"); 						
 						RpeCustomClassificationType.setAttribute("EntityID","TARIF_INTEGRE_DE_LA_COMMUNAUTE");
 						RpeCustomClassificationType.setAttribute("Type", "Rpe_CustomClassificationType");
 						 Element cCCValueElement = doc.createElement("Value");
 						cCCValueElement.setAttribute("AttributeID", attrib);
 						cCCValueElement.setAttribute("QualifierID", "AllCountries");
 						cCCValueElement.setTextContent(lValue);
 						cCCMetaData.appendChild(cCCValueElement);
 						RpeCustomClassificationType.appendChild(cCCMetaData);
 						Element RpeINTRACustomClassificationType= doc.createElement("EntityCrossReference");
 						Element iNTRMetaData = doc.createElement("MetaData"); 
 						RpeINTRACustomClassificationType.setAttribute("EntityID","INTRASTAT");
 						RpeINTRACustomClassificationType.setAttribute("Type", "Rpe_CustomClassificationType");
 						 Element INTRASTATElement = doc.createElement("Value");
 						INTRASTATElement.setAttribute("AttributeID", attrib);
 						INTRASTATElement.setAttribute("QualifierID", "AllCountries");
 						INTRASTATElement.setTextContent(lValue.substring(0, 8));
 						iNTRMetaData.appendChild(INTRASTATElement);
 						RpeINTRACustomClassificationType.appendChild(iNTRMetaData);
 							 						
 						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
 							variantElement.appendChild(RpeCustomClassificationType);
 							variantElement.appendChild(RpeINTRACustomClassificationType);
	 						}
 						}
 						} }catch(Exception e){
						 System.out.println("CCT----------"+ErrorPath);
						 continue;						 
					 }
					 /*------------------ECR--------------------*/
					 try{
					if (fullPath.equals(Configpath) && category.equals("ECR")) {
									//	 	logger.info("Inside ECR");
	 						Element valueElement = doc.createElement("Value");	 						
	 						if(attrib.equals("Att_Diet_Typ")){	 						
	 						entityCrosseRef = doc.createElement("EntityCrossReference");	 						
	 						entityCrosseRef.setAttribute("EntityID",lValue);
	 						entityCrosseRef.setAttribute("Type", "Rpe_DietType_Info");
	 						valide=true;
	 						}	 						
	 						if(valide){	 							
	 						if(attrib.equals("Att_Is_Diet_mark_On_the_Pack")){	 					
	 							Element entityCrosseMeta = doc.createElement("MetaData");
	 							valueElement.setAttribute("AttributeID", attrib);
	 							valueElement.setAttribute("QualifierID", "AllCountries");	 							
	 							valueElement.setAttribute("ID", lValue.equals("true") ? "Y" : "N");
	 							entityCrosseMeta.appendChild(valueElement);
	 							entityCrosseRef.appendChild(entityCrosseMeta);
	 							valide=false;
	 							}
	 						variantElement.appendChild(entityCrosseRef);
	 						}	 						
	 					}
					 }catch(NullPointerException e){
						 System.out.println("Rpe_DietType_Info----"+e.getMessage());
						 System.out.println("Rpe_DietType_Info----"+ErrorPath);
						 continue;
						 
					 }
					 /*----------------------TEMP---------------------------------*/
					 try{
							if (fullPath.equals(Configpath) && category.equals("TEMP")) {
									Element valueElement = doc.createElement("Value");
			 						if(tempStatus){
			 						if(value.contains("storageHandling")){			 						
			 						tempEntityCrosseRef = doc.createElement("EntityCrossReference");			 						
			 						tempEntityCrosseRef.setAttribute("EntityID","STORAGE_HANDLING");
			 						tempEntityCrosseRef.setAttribute("Type", "Rpe_Temperature");
			 						tempStatus=false;
			 						}
			 						}	
			 							valueElement.setAttribute("AttributeID", attrib);
			 							valueElement.setAttribute("UnitID", "unece.unit.CEL");
			 							valueElement.setAttribute("QualifierID", "AllCountries");			 					
			 							valueElement.setTextContent(lValue);    
			 							tempMetaData.appendChild(valueElement);
			 							tempEntityCrosseRef.appendChild(tempMetaData);	
			 							//}	
			 							if("EA".equals(ProdId.get(productId))  || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
			 							variantElement.appendChild(tempEntityCrosseRef);
			 							}else{
			 								productElement.appendChild(tempEntityCrosseRef);
			 							}
			 					}
							 }catch(Exception e){
								 System.out.println("TEMP Error Path----"+ErrorPath);
								 System.out.println("TEMP Error Path----"+e.getMessage());
								 
								 continue;
								 
							 }
					 /*----------------------TEMP End---------------------------------*/
					/**-------------------Rpe_AdditionalClassification ----------------**/
//					 try{
//					if (fullPath.equals(Configpath) && category.equals("REPAC")) {
//					 	
// 						Element valueElement = doc.createElement("Value"); 
// 						Element constantvalueElement = doc.createElement("Value"); 
// 						Element rPCATEntityCrosseRef = doc.createElement("EntityCrossReference");
// 						rPCATEntityCrosseRef.setAttribute("EntityID","Carrefour_Features");
// 						rPCATEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalClassification");
// 						if(value.equals("specificProductFormat")){
// 							Element rPCATEntityCrosseMeta=doc.createElement("MetaData");
// 							valueElement.setAttribute("AttributeID", attrib);
// 							valueElement.setTextContent(lValue);;
// 							rPCATEntityCrosseMeta.appendChild(valueElement);
// 							rPCATEntityCrosseRef.appendChild(rPCATEntityCrosseMeta);	
// 							}else {
// 								Element rPCATEntityCrosseMeta=doc.createElement("MetaData"); 	 							
// 	 							valueElement.setAttribute("AttributeID", attrib);
// 	 							valueElement.setAttribute("QualifierID", "AllCountries");
// 	 							valueElement.setAttribute("ID",lValue);
// 	 							rPCATEntityCrosseMeta.appendChild(valueElement);
// 	 							rPCATEntityCrosseRef.appendChild(rPCATEntityCrosseMeta);
// 								
// 							}	
// 						productElement.appendChild(rPCATEntityCrosseRef);
// 					}
//					 }catch(NullPointerException e){
//						 System.out.println("Rpe_AdditionalClassification----"+ErrorPath);
//						 continue;
//						 
//					 }
					 
					 try{
							if (fullPath.equals(Configpath) && category.equals("REPAC")) {
								Element valueElement = doc.createElement("Value"); 
								Element constantvalueElement = doc.createElement("Value"); 
								Element rPCATEntityCrosseRef = doc.createElement("EntityCrossReference");
								rPCATEntityCrosseRef.setAttribute("EntityID","Carrefour_Features");
								rPCATEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalClassification");
								if(value.equals("specificProductFormat")){
									Element rPCATEntityCrosseMeta=doc.createElement("MetaData");
									valueElement.setAttribute("AttributeID", attrib);
									valueElement.setTextContent(lValue);
									constantvalueElement.setAttribute("AttributeID", "Att_Add_Tra_class_prop");
									constantvalueElement.setAttribute("ID", "F");
//									constantvalueElement.setTextContent("F");
									rPCATEntityCrosseMeta.appendChild(constantvalueElement);
									rPCATEntityCrosseMeta.appendChild(valueElement);
									rPCATEntityCrosseRef.appendChild(rPCATEntityCrosseMeta);	
									}else {
										Element rPCATEntityCrosseMeta=doc.createElement("MetaData"); 	 							
			 							valueElement.setAttribute("AttributeID", attrib);
//			 							valueElement.setAttribute("QualifierID", "AllCountries");
			 							valueElement.setAttribute("ID",lValue);
			 							constantvalueElement.setAttribute("AttributeID", "Att_Add_Tra_class_prop");
			 							constantvalueElement.setAttribute("ID", "T");
//			 							constantvalueElement.setTextContent("T");
			 							rPCATEntityCrosseMeta.appendChild(constantvalueElement);
			 							rPCATEntityCrosseMeta.appendChild(valueElement);
			 							rPCATEntityCrosseRef.appendChild(rPCATEntityCrosseMeta);
									}	
								productElement.appendChild(rPCATEntityCrosseRef);
							}
							 }catch(NullPointerException e){
								 System.out.println("Rpe_AdditionalClassification----"+ErrorPath);
								 continue;
							 }
					/** --------------------Rpe_AdditionalRegulatoryClassification------------------ **/
					try{
               if (fullPath.equals(Configpath) && category.equals("REPARC")) {					 	
 						Element valueElement = doc.createElement("Value"); 					 
 						//System.out.println("REPARC----"+value);
 						Element rEPARCEntityCrosseRef = doc.createElement("EntityCrossReference");
 						if(value.contains("cheese")){ 							
 							rEPARCEntityCrosseRef.setAttribute("EntityID","CCG");
 							rEPARCEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalRegulatoryClassification"); 						
 						}
 						else if(value.contains("ECR")){ 							
 							rEPARCEntityCrosseRef.setAttribute("EntityID","ECR");
 							rEPARCEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalRegulatoryClassification"); 						
 						}
 						else if(value.contains("product")){ 							
 							rEPARCEntityCrosseRef.setAttribute("EntityID","MIV-C");
 							rEPARCEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalRegulatoryClassification"); 						
 						}else{
 							rEPARCEntityCrosseRef.setAttribute("EntityID","OKPD2");
 							rEPARCEntityCrosseRef.setAttribute("Type", "Rpe_AdditionalRegulatoryClassification");
 						}
 						
 						Element rEPARCEntityCrosseMeta = doc.createElement("MetaData");
 							valueElement.setAttribute("AttributeID", attrib);
 							valueElement.setAttribute("QualifierID", "AllCountries");
 							valueElement.setTextContent(lValue);;
 							rEPARCEntityCrosseMeta.appendChild(valueElement);
 							rEPARCEntityCrosseRef.appendChild(rEPARCEntityCrosseMeta);	
 							
 						productElement.appendChild(rEPARCEntityCrosseRef);
 					}
					 }catch(NullPointerException e){
						 System.out.println("Rpe_AdditionalRegulatoryClassification----"+ErrorPath);
						 continue;
						 
					 }
                    /**---------- Rpe_AddTradeItemIdentificationType---------------**/
					try{
               if (fullPath.equals(Configpath) && category.equals("REPAIC")) {	
            	  // System.out.println("REPAIC----"+ErrorPath);
					Element valueElement = doc.createElement("Value");
					if(attrib.equals("Att_add_Tra_Ide_Typ")){
						rEPAICEntityCrosseRef = doc.createElement("EntityCrossReference");
						rEPAICEntityCrosseRef.setAttribute("EntityID",lValue);
						rEPAICEntityCrosseRef.setAttribute("Type", "Rpe_AddTradeItemIdentificationType"); 						
					}
					if(attrib.equals("Att_Prov_add_cod")){
						Element rEPAICEntityCrosseMeta =doc.createElement("MetaData");						
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lValue);;
						rEPAICEntityCrosseMeta.appendChild(valueElement);
						rEPAICEntityCrosseRef.appendChild(rEPAICEntityCrosseMeta);
						productElement.appendChild(rEPAICEntityCrosseRef);
						}	
					
				}
					 }catch(NullPointerException e){
						 System.out.println("AddTradeItemIdentificationType----"+ErrorPath);
						 continue;
						 
					 }
					  /**---------- Rpe_AddTradeItemIdentificationType NumberForWeight & NumberForPrice ---------------**/
					try{
               if (fullPath.equals(Configpath) && category.equals("REPAIT")) {	
            	  // System.out.println("REPAIC----"+ErrorPath);
					Element valueElement = doc.createElement("Value");
					//if(attrib.equals("Att_add_Tra_Ide_Typ")){
					Element aITEntityCrosseRef = doc.createElement("EntityCrossReference");
					aITEntityCrosseRef.setAttribute("EntityID","PLU");
					aITEntityCrosseRef.setAttribute("Type", "Rpe_AddTradeItemIdentificationType"); 						
					//}
					//if(attrib.equals("Att_Prov_add_cod")){
						Element rEPAICEntityCrosseMeta =doc.createElement("MetaData");						
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setTextContent(lValue);;
						rEPAICEntityCrosseMeta.appendChild(valueElement);
						aITEntityCrosseRef.appendChild(rEPAICEntityCrosseMeta);	
					//	}	
					productElement.appendChild(aITEntityCrosseRef);
				}
					 }catch(NullPointerException e){
						 System.out.println("AddTradeItemIdentificationType----"+ErrorPath);
						 continue;
						 
					 }
				
               /*-------------------Communication Channel----------------------------*/
					 try{
				if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("CCHANNEL")) {
					
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						if(attrib.equals("CMARKET")){
						//	System.out.println("-----CMARKET------------");
							marketN=lValue;
							address = doc.createElement("Value");
							contactName = doc.createElement("Value");
							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
  								variantElement.appendChild(variantValues);
  							}else{
  								productElement.appendChild(variantValues);  								
  							}
						}						
						//productElement.appendChild(mValueGroup);
						if(attrib.equals("Att_CXC_ContactName")){						
							contactName = doc.createElement("Value");
							contactName.setAttribute("AttributeID", attrib);
							contactName.setAttribute("QualifierID", marketN);
							contactName.setTextContent(lValue);
							variantValues.appendChild(contactName);
							/*Element	cName = doc.createElement("Value");
							cName.setAttribute("AttributeID", attrib);
							cName.setAttribute("QualifierID", "AllCountries");
							cName.setTextContent(lValue);
							variantValues.appendChild(cName);
*/						}
						if(attrib.equals("Att_CXC_Address1")){						
							address = doc.createElement("Value");
							address.setAttribute("AttributeID", attrib);
							address.setAttribute("QualifierID", marketN);
							address.setTextContent(lValue);
							variantValues.appendChild(address);
							/*Element	cAdress = doc.createElement("Value");
							cAdress.setAttribute("AttributeID", attrib);
							cAdress.setAttribute("QualifierID", "AllCountries");
							cAdress.setTextContent(lValue);
							variantValues.appendChild(cAdress);*/
						}
						if(fullPath.contains("code")){
						if(lValue.equals("EMAIL")){	
							CCHANNEL = doc.createElement("Value");
							CCHANNEL.setAttribute("AttributeID", "Att_CXC_Email");
							/*allContries = doc.createElement("Value");
							allContries.setAttribute("AttributeID", "Att_CXC_Email");*/
						}
						else if(lValue.equals("TELEPHONE")){
							CCHANNEL = doc.createElement("Value");
							CCHANNEL.setAttribute("AttributeID", "Att_CXC_Tel");
							/*allContries = doc.createElement("Value");
							allContries.setAttribute("AttributeID", "Att_CXC_Tel");*/
						}
						else if(lValue.equals("WEBSITE") || lValue.equals("MOBILE_WEBSITE")){
							CCHANNEL = doc.createElement("Value");
							CCHANNEL.setAttribute("AttributeID", "Att_CXC_WebSite");
							/*allContries = doc.createElement("Value");
							allContries.setAttribute("AttributeID", "Att_CXC_WebSite");*/
						}
						CCHANNEL.setAttribute("QualifierID", marketN);
						valueElements.get(productId).add(CCHANNEL);
						ChannelValue=lValue;
						//allContries.setAttribute("QualifierID", "AllCountries");
						}
						if(fullPath.contains("value")){							
							CCHANNEL.setTextContent(lValue);
							variantValues.appendChild(CCHANNEL);
							/*allContries.setTextContent(lValue);
							variantValues.appendChild(allContries);*/

							
						}
						
				}
					 }catch(NullPointerException e){
						 System.out.println("CCHANNEL----------"+ErrorPath);
						 continue;
						 
					 }
				/*-------------------Communication Channel----------------------------*/				
					 try{
							// if (fullPath.equals(Configpath) && category.equals("LIFESTYLE")) {
							 if(lifstat==0){
								 //Date Format for Archived
								 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
								 Date currentDate = new Date();
								 
								 if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
									 Element valueElement = doc.createElement("Value");	
									 Element Lif_cyc_sta = doc.createElement("ValueGroup");
									 Lif_cyc_sta.setAttribute("AttributeID", "Att_Lif_cyc_sta");
									 String Status = result.get(productId);
									 //Archived Logic for UC
									 Date endAvilDateTime=null;
									 
									 
									 String endAvilDate = dataM.get(productId).getOrDefault("endAvailabilityDateTime","NoDate");
									 
									 if (endAvilDate != null && !endAvilDate.trim().isEmpty() && !endAvilDate.equals("NoDate")) {
										 endAvilDateTime = sdf.parse(endAvilDate);
									 }
									 
									 	 
									 if(!SavenciaCaseID.containsValue(productId) && isItemPk.containsValue(productId)&&(endAvilDateTime!=null?endAvilDateTime.before(currentDate):false)){
										 valueElement.setAttribute("QualifierID", "AllCountries");
										 valueElement.setAttribute("ID", "8");//lifecycle status = Deactivated 8
									 }else{
									
									 if(Status.equals("GDSStatus")){
										 valueElement.setAttribute("QualifierID", "AllCountries");
								         valueElement.setAttribute("ID", "7");
								     //  System.out.println("Initial productID----"+ productId+"status--- "+Status);
									 }else if(Status.equals("statusERP")){
										 valueElement.setAttribute("QualifierID", "AllCountries");
										 valueElement.setAttribute("ID", "6");		 
									//	 System.out.println("Initial productID----"+ productId+"status--- "+Status);
									 }else if(Status.equals("statusGTIN")){
										 valueElement.setAttribute("QualifierID", "AllCountries");
										 valueElement.setAttribute("ID", "4");	
									//	 System.out.println("Initial productID----"+ productId+"status--- "+Status);
									 }
									 }
									 Lif_cyc_sta.appendChild(valueElement);
									valuesElement.appendChild(Lif_cyc_sta);
																 
								 lifstat=1;
							 }else{
									 Element Lif_cyc_sta = doc.createElement("ValueGroup");
									 Element valueElement = doc.createElement("Value");							
									 Lif_cyc_sta.setAttribute("AttributeID", "Att_Lif_cyc_sta");
									 String Status = ulresult.get(productId);
									 		 
									 //Archive Logic for UL Level
									 Date endAvilDateTime2=null;
//									 
									 String endAvilDate2 = dataM.get(productId).getOrDefault("endAvailabilityDateTime","NoDate");
									 
									 if (endAvilDate2 != null && !endAvilDate2.trim().isEmpty() && !endAvilDate2.equals("NoDate")) {
										 endAvilDateTime2 = sdf.parse(endAvilDate2);
									 }
									 
									 
									 if(endAvilDateTime2!=null?endAvilDateTime2.before(currentDate):false){
										 valueElement.setAttribute("QualifierID", "AllCountries");
										 valueElement.setAttribute("ID", "12");//lifecycle status = Completed 12
									 }else{
										 
									 if(Status !=null){
								//	 System.out.println("Initial productID----"+ productId+"status--- "+Status);
									 if(Status.equals("statusAPO")){
										 valueElement.setAttribute("QualifierID", "AllCountries");
									       valueElement.setAttribute("ID", "11");
										 }else if(Status.equals("statusERP")){
											 valueElement.setAttribute("QualifierID", "AllCountries");
											// valueElement.setAttribute("ID", "7");
											 valueElement.setAttribute("ID", "12");
										 }else if(Status.equals("statusGTIN")){
											 valueElement.setAttribute("QualifierID", "AllCountries");
											 valueElement.setAttribute("ID", "11");		 
										 } 
									 }
									 }
									 
									 Lif_cyc_sta.appendChild(valueElement);
									 valuesElement.appendChild(Lif_cyc_sta);		 
								 }
								 lifstat=1;	
							}
							 
						 }catch(NullPointerException e){
							 System.out.println("LIFESTYLE----------"+ErrorPath);
							 continue;
						 }
			 
				/**---------- Att_Lif_cyc_sta Attribute mapping Ending-----------------**/
					 /**----------------------Att_Context-----------------------**/
							 try{
  					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("CONTEXT")) {
  									
  						if(attrib.equals("Att_Prd_site")){
  							Msiterole=contextProp.getProperty(lValue);  					       
  						}
  						
  						if(attrib.equals("Att_Main_Prd_site_Req_field")){
  							Element crossValue = doc.createElement("Value");
  							Element contexValue = doc.createElement("Value");
  							if(lValue.equals("true")){
  							crossValue.setAttribute("AttributeID", "Att_Context");  							
  						//	crossValue.setAttribute(  "ID" , context(fRtable.get(Msiterole)));  							
  							crossValue.setAttribute(  "ID" , contextProp.getProperty(contextProp.getProperty(Msiterole)));
  					 		contexValue.setAttribute("AttributeID", "Att_Context");
  					 		contexValue.setAttribute(  "ID" , contextProp.getProperty(contextProp.getProperty(Msiterole)));
  							}
  							if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
  								variantValues.appendChild(crossValue);
  								valuesElement.appendChild(contexValue);							
  							}else{
  								valuesElement.appendChild(crossValue);
  							}  							
  						}  						 						
  					} 
  					}catch(NullPointerException e){
  						 System.out.println("CONTEXT Error----------"+ErrorPath);
  						 System.out.println("CONTEXT Error----------"+e.getMessage());
  						 continue;
  						 
  					 }	
               
               try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("LOVM")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
					//	System.out.println("LOVM------------"+attrib);
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						mValueGroup.setAttribute("AttributeID", attrib);
						//productElement.appendChild(mValueGroup);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);						
						 if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							mValueGroup.appendChild(valueElement);
							variantValues.appendChild(mValueGroup);
						} else {
							mValueGroup.appendChild(valueElement);
							valuesElement.appendChild(mValueGroup);							
						}
						}else{
							if(!Arrays.toString(proudctonly).contains(attrib)){
								mValueGroup.appendChild(valueElement);
							    valuesElement.appendChild(mValueGroup);	
							}
						}
						 valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("LOVM----------"+ErrorPath);
						 continue;
						 
					 }
               try{
					if (fullPath.equals(Configpath) && category.equals("LOVMWQ")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						mValueGroup.setAttribute("AttributeID", attrib);
						//productElement.appendChild(mValueGroup);
						valueElement.setAttribute("ID", lValue);
					//	valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);						
						 if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							mValueGroup.appendChild(valueElement);
							variantValues.appendChild(mValueGroup);
						} else {
							mValueGroup.appendChild(valueElement);
							valuesElement.appendChild(mValueGroup);							
						}
						}else{
							if(!Arrays.toString(proudctonly).contains(attrib)){
								mValueGroup.appendChild(valueElement);
							    valuesElement.appendChild(mValueGroup);	
							}
						}
						 valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("LOVM----------"+ErrorPath);
						 continue;
						 
					 }
             try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("NORQV")) {
						Element valueElement = doc.createElement("Value");
						Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						productElement.appendChild(mValueGroup);
						mValueGroup.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue);
						mValueGroup.appendChild(valueElement);
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {

							variantValues.appendChild(mValueGroup);
						} else {
							valuesElement.appendChild(mValueGroup);
						}
						}else {
							valuesElement.appendChild(mValueGroup);
						}
						valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("NORQV----------"+ErrorPath);
						 continue;
						 
					 }
               try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("NORVA")) {
						Element valueElement = doc.createElement("Value");
						//Element mValueGroup = doc.createElement("ValueGroup");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						//productElement.appendChild(mValueGroup);
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue);
						//mValueGroup.appendChild(valueElement);
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {

							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else {
							valuesElement.appendChild(valueElement);
						}
						valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("NORVA----------"+ErrorPath);
						 continue;
						 
					 }
                  try{
					if (fullPath.equals(Configpath) && category.equals("LOVMTF")) {
						Element valueElement = doc.createElement("Value");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						//System.out.println("inside");
					//	System.out.println("LOVMTF----------"+attrib);						
						valueElement.setAttribute("AttributeID", attrib);
						if(attrib.equals("Att_Gen_Mod_itm") || attrib.equals("Att_Inv_Unit")|| attrib.equals("Att_Ord_Unit")|| attrib.equals("Att_Trd_itm_mark_as_Rec")
								|| attrib.equals("Att_Irdt_itm")){
							valueElement.setAttribute("QualifierID", "AllCountries");
						}
						valueElement.setAttribute("ID", lValue.equals("true") ? "Y" : "N");
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else {
						if (!Arrays.toString(proudctonly).contains(attrib) && !Arrays.toString(onlyvariantAttributes).contains(attrib)) {
							valuesElement.appendChild(valueElement);
						}
						}
							} }catch(NullPointerException e){
								 System.out.println("LOVMTF----------"+ErrorPath);
								 continue;								 
							 }
                  try{
					if (fullPath.equals(Configpath) && category.equals("LOVSF")) {
						Element valueElement = doc.createElement("Value");
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						//System.out.println("inside");
						valueElement.setAttribute("AttributeID", attrib);
						//valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setAttribute("ID", lValue.equals("SFUC") ? "Y" : "N");
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else {
							if (!Arrays.toString(variantattributes).contains(attrib)) {
							valuesElement.appendChild(valueElement);
						}
						}
							} }catch(NullPointerException e){
								 System.out.println("LOVTFA----------"+ErrorPath);
								 continue;
								 
							 }
					try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("LOVTF")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	System.out.println("LOVTF----------"+attrib+"-------"+productId);
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(attrib.equals("Att_Hsld_Serv_size")){
						valueElement.setTextContent(lValue.replace(",", "."));
						}
						else if(attrib.equals("Att_mark_Fam")){							
								valueElement.setTextContent(commerialfamily(lValue));							
						}
						else{
							valueElement.setTextContent(lValue);
						}
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							if(!attrib.equals("Att_Is_Trd_itm_pac_irr")){
							valuesElement.appendChild(valueElement);
							}
						}
						}else{
							if(!Arrays.toString(proudctonly).contains(attrib) && !Arrays.toString(onlyvariantAttributes).contains(attrib)) {
							valuesElement.appendChild(valueElement);
							} 
						}
					} }catch(NullPointerException e){
						 System.out.println("LOVTF----------"+ErrorPath);
						 continue;						 
					 }
					/*----------Rpe_Variant_Serving_Size-----------------*/
					try{
					if (fullPath.equals(Configpath) && category.equals("RVSS")) {
						
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						VarientServicSize=doc.createElement("MetaData");
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue);
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							vSSmetaData.appendChild(valueElement);
							servingSizeId.appendChild(vSSmetaData);
							//variantValues.appendChild(valueElement);
						} 
						}
					} }catch(NullPointerException e){
						 System.out.println("RVSS Error----------"+ErrorPath);
						 continue;						 
					 }
					
					try{						
					if ((fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("RVSSD"))) {
					//	System.out.println("RVSSD ----------"+attrib);
						String index=fullPath.substring(43,44);						
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																					// surrounding
																				// brackets
					
						String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(lValue);
				        Element valueGroup = doc.createElement("ValueGroup");
						valueGroup.setAttribute("AttributeID", attrib);						
						Map<String,String> map=new HashMap<>();
				        while (matcher.find()) {
				            String language = matcher.group(1); // Extract the language				            
				            String text = matcher.group(2);     // Extract the text	
				          
				            Element rVSSD = doc.createElement("Value");
				            rVSSD.setAttribute("QualifierID", Language(language));
				            rVSSD.setTextContent(text);					            
							valueGroup.appendChild(rVSSD);
							map.put(language, text);
				        }
				        if(!map.containsKey("en")){	
                            if(map.containsKey("fr")){
                          	  String fValue = map.get("fr");
                          	 Element Lvalue = doc.createElement("Value");								
    							Lvalue.setAttribute("QualifierID", "en-US");
    							Lvalue.setTextContent(fValue);
    							valueGroup.appendChild(Lvalue);
							}else{                                      
						for (Entry<String, String> lang : map.entrySet()) {
						//	System.out.println("Inside ---------"+lang.getKey());
						if(!"fr".equals(lang.getKey())){
						Element Lvalue = doc.createElement("Value");								
						Lvalue.setAttribute("QualifierID", "en-US");
						Lvalue.setTextContent(lang.getValue());
						valueGroup.appendChild(Lvalue);	
						break;
						}
						}	
						}
						}
                           if(index.equals(SSIndex)){						
								vSSmetaData.appendChild(valueGroup);
								servingSizeId.appendChild(vSSmetaData);	
								}								
					
					} }catch(Exception e){
						System.out.println("RVSSD Error----------"+e.getMessage());
						 System.out.println("RVSSD Error----------"+ErrorPath);
						 continue;
						 
					 }
					/*----------Rpe_Variant_Serving_Size End-----------------*/
					// FOR MR47
					/*try{
					if (fullPath.equals(Configpath) && category.equals("LOVTFL")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						String LV=getLookupValue(attrib, lValue);
						valueElement.setTextContent(LV);
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else{
							valuesElement.appendChild(valueElement);
						}
					} }catch(NullPointerException e){
						 System.out.println("LOVTFL----------"+ErrorPath);
						 continue;						 
					 }*/

							/*-----------------YODA KEY AND QTY Starting--------------*/
					try{
		               if (fullPath.equals(Configpath) && category.equals("TEST")) {
		            	  	            	  
		            	   Element  Qvalue = doc.createElement("Value");		            	   
							if(attrib.equals("KEY")){
								 if(UserTypeID.equals("Savencia_Case")){
									 TradeItemPackRefElement = doc.createElement("ProductCrossReference");
										TradeItemPackRefElement.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");
				                      }
								if(UserTypeID.equals("Savencia_Pallet")){
									TradeItemPackRefElement_pallet = doc.createElement("ProductCrossReference");
									TradeItemPackRefElement_pallet.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");
		                        }
		                        if(UserTypeID.equals("Savencia_Display_Shipper")){
		                        	TradeItemPackRefElement_dS = doc.createElement("ProductCrossReference");
									TradeItemPackRefElement_dS.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");
		                        }
		                        if(UserTypeID.equals("Savencia_Transport_Load")){
		                        	TradeItemPackRefElement_tL = doc.createElement("ProductCrossReference");
		                        	TradeItemPackRefElement_tL.setAttribute("Type", "Rpp_TradeItemReference_TL");
		                        	
		                        }
		                        if((!BaseUnite.equals("false") && !ConsumerUnite.equals("true"))){
		                        if(UserTypeID.equals("Savencia_Pack")){
		                        	TradeItemPackRefElement_pack = doc.createElement("ProductCrossReference");
		                        	TradeItemPackRefElement_pack.setAttribute("Type", "Rpp_TradeItemReference_PK");
		                        }
		                        }
		                        if(UserTypeID.equals("Savencia_Mixed_Module")){
		                        	TradeItemPackRefElement_mM = doc.createElement("ProductCrossReference");
		                        	TradeItemPackRefElement_mM.setAttribute("Type", "Rpp_TradeItemReference_CA_DS_PL_MM");
		                        	
		                        }								
								YODAKEY = doc.createElement("KeyValue");
								
							if("EA".equals(ProdId.get(lValue)) || "PK".equals(ProdId.get(lValue))){
								YODAKEY.setAttribute("KeyID","Variant_Key");
								YODAKEY.setTextContent(lValue+"001");	
								//Qvalue.setTextContent(lValue+"001");
								//YODAKEY.appendChild(Qvalue);
								        if(UserTypeID.equals("Savencia_Case")){
										TradeItemPackRefElement.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pallet")){
				                        	TradeItemPackRefElement_pallet.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Display_Shipper")){
				                        	TradeItemPackRefElement_dS.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Transport_Load")){
				                        	TradeItemPackRefElement_tL.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pack") &&(!BaseUnite.equals("false") && !ConsumerUnite.equals("true"))){
				                        	TradeItemPackRefElement_pack.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pack")&&((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
				                        	TradeItemPackRefElement_pack.setAttribute("Type", "Rpp_Pack_UC");
				                        	YODAKEY.setAttribute("KeyID","YODA_Key");
											YODAKEY.setTextContent(lValue);
											TradeItemPackRefElement_pack.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Mixed_Module")){
				                        	TradeItemPackRefElement_mM.appendChild(YODAKEY);
				                        }
								//TradeItemPackRefElement_dS.appendChild(YODAKEY);
							}else if(((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								YODAKEY.setAttribute("KeyID","Variant_Key");
								YODAKEY.setTextContent(lValue+"001");	
								//Qvalue.setTextContent(lValue+"001");
								//YODAKEY.appendChild(Qvalue);
								        if(UserTypeID.equals("Savencia_Case")){
										TradeItemPackRefElement.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pallet")){
				                        	TradeItemPackRefElement_pallet.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Display_Shipper")){
				                        	TradeItemPackRefElement_dS.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Transport_Load")){
				                        	TradeItemPackRefElement_tL.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pack") &&(!BaseUnite.equals("false") && !ConsumerUnite.equals("true"))){
				                        	TradeItemPackRefElement_pack.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Pack")&&((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
				                        	TradeItemPackRefElement_pack.setAttribute("Type", "Rpp_Pack_UC");
				                        	YODAKEY.setAttribute("KeyID","YODA_Key");
											YODAKEY.setTextContent(lValue);
											TradeItemPackRefElement_pack.appendChild(YODAKEY);
				                        }
				                        if(UserTypeID.equals("Savencia_Mixed_Module")){
				                        	TradeItemPackRefElement_mM.appendChild(YODAKEY);
				                        }
								
							}
							else{
								/*if(lValue.substring(lValue.length()-2).equals("00")){
									YODAKEY.setAttribute("KeyID", "Variant_Key");
									YODAKEY.setTextContent(lValue+"001");
								}else{*/
									
								//}
								String UCP =SPCode.get(lValue);  
								        if(UserTypeID.equals("Savencia_Case")){								        	
								        	if(UCP != null && UCP.equals("UCPACK")){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{								        		
								        		if(lValue.substring(lValue.length()-2).equals("00")){
													YODAKEY.setAttribute("KeyID", "Variant_Key");
													YODAKEY.setTextContent(lValue+"001");
												}else{
												YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);	
												}
										     TradeItemPackRefElement.appendChild(YODAKEY);
								        	}
				                        }
				                        if(UserTypeID.equals("Savencia_Pallet")){
				                        	if(UCP != null && UCP.equals("UCPACK")){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{
								        		YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);
								        		TradeItemPackRefElement_pallet.appendChild(YODAKEY);
								        	}
				                        }
				                        if(UserTypeID.equals("Savencia_Display_Shipper")){
				                        	if(((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{
								        		if(lValue.substring(lValue.length()-2).equals("00")){
													YODAKEY.setAttribute("KeyID", "Variant_Key");
													YODAKEY.setTextContent(lValue+"001");
												}else{
												YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);	
												}
				                        	TradeItemPackRefElement_dS.appendChild(YODAKEY);
								        	}
				                        }
				                        if(UserTypeID.equals("Savencia_Transport_Load")){
				                        	if(((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{
								        		YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);
				                        	TradeItemPackRefElement_tL.appendChild(YODAKEY);
								        	}
				                        }
				                        if(UserTypeID.equals("Savencia_Pack") &&(!BaseUnite.equals("false") && !ConsumerUnite.equals("true"))){
				                        	if(((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{
								        		if(lValue.substring(lValue.length()-2).equals("00")){
													YODAKEY.setAttribute("KeyID", "Variant_Key");
													YODAKEY.setTextContent(lValue+"001");
												}else{
												YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);	
												}
				                        	TradeItemPackRefElement_pack.appendChild(YODAKEY);
				                        }
				                        }
				                       
				                        if(UserTypeID.equals("Savencia_Mixed_Module")){
				                        	if(((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								        		YODAKEY.setAttribute("KeyID","Variant_Key");
												YODAKEY.setTextContent(lValue+"001");	
												TradeItemPackRefElement.appendChild(YODAKEY);
								        	}else{
								        		YODAKEY.setAttribute("KeyID", "YODA_Key");
												YODAKEY.setTextContent(lValue);
				                        	TradeItemPackRefElement_mM.appendChild(YODAKEY);
								        	}
				                        }
							//}
							}
							}
							if(attrib.equals("Att_Qty")){
								Meta= doc.createElement("MetaData");	
								Element valueElement = doc.createElement("Value");								
								valueElement.setAttribute("AttributeID", attrib);
								valueElement.setTextContent(lValue);;
								Meta.appendChild(valueElement);
								//YODAKEY.appendChild(Meta);	
								if(UserTypeID.equals("Savencia_Case")){
									TradeItemPackRefElement.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Pallet")){
			                        	TradeItemPackRefElement_pallet.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Display_Shipper")){
			                        	TradeItemPackRefElement_dS.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Transport_Load")){
			                        	TradeItemPackRefElement_tL.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Pack")){
			                        	TradeItemPackRefElement_pack.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Mixed_Module")){
			                        	TradeItemPackRefElement_mM.appendChild(Meta);
			                        }
			                        if(UserTypeID.equals("Savencia_Case")){
			                        	productElement.appendChild(TradeItemPackRefElement);
				                        }
				                        if(UserTypeID.equals("Savencia_Pallet")){
				                        	productElement.appendChild(TradeItemPackRefElement_pallet);
				                        }
				                        if(UserTypeID.equals("Savencia_Display_Shipper")){
				                        	productElement.appendChild(TradeItemPackRefElement_dS);
				                        }
				                        if(UserTypeID.equals("Savencia_Transport_Load")){
				                        	productElement.appendChild(TradeItemPackRefElement_tL);
				                        }
				                        if(UserTypeID.equals("Savencia_Pack")){
				                        	productElement.appendChild(TradeItemPackRefElement_pack);
				                        }
				                        if(UserTypeID.equals("Savencia_Mixed_Module")){
				                        	productElement.appendChild(TradeItemPackRefElement_mM);
				                        }}	
						} }catch(NullPointerException e){
							 System.out.println("TEST----------"+ErrorPath);
							 System.out.println("ERROR----------"+e.getMessage());
							 continue;							 
						 }
		               /*-------------YODA KEY AND QTY Ending--------------------*/				
					/*--------------------------- Att_Prd_img------------------*/
					try{
					if (fullPath.equals(Configpath) && category.equals("MULTI")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						imageValues.setAttribute("AttributeID", attrib);						
						valueElement.setAttribute("QualifierID", "AllCountries");						
						valueElement.setAttribute("ID", lValue);						                      
						imageValues.appendChild(valueElement); 
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(imageValues);
						} else {
							valuesElement.appendChild(imageValues);
						}
                       }
					 }catch(NullPointerException e){
						 System.out.println("IMAGE----------"+ErrorPath);
						 continue;						 
					 }
					/*--------------------------- Att_Prd_img------------------*/	
					/*--------------------------- targetMarketCountryCode :Att_GS1_tgt_mark------------------*/
					try{
					if (fullPath.equals(Configpath) && category.equals("TMCC")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						targetMarketCode.setAttribute("AttributeID", attrib);
						Element valueElement = doc.createElement("Value");
						//valueElement.setAttribute("AttributeID", attrib);						
						valueElement.setAttribute("ID", lValue);
						                     
						targetMarketCode.appendChild(valueElement); 
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(targetMarketCode);
						} else {
							valuesElement.appendChild(targetMarketCode);
						}
                       }
					 }catch(NullPointerException e){
						 System.out.println("TMCC----------"+ErrorPath);
						 continue;						 
					 }
					/*--------------------------- targetMarketCountryCode------------------*/
		
					
					/*--------------------------MR49----------------------------------*/
					try{
					if (fullPath.equals(Configpath) && category.equals("MR49")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(lValue.equals("BOIL") ||lValue.equals("BHEAT_AND_SERVEOIL") ){
						valueElement.setTextContent("80");
						variantValues.appendChild(valueElement);
						}
						else if(lValue.equals("RECONSTITUTE") ||lValue.equals("WHIP")){
						valueElement.setTextContent("50");
						variantValues.appendChild(valueElement);
						}/*else{
							valueElement.setTextContent("");
						}*/
						valueElements.get(productId).add(valueElement);
						//variantValues.appendChild(valueElement);
						
					} }catch(NullPointerException e){
						 System.out.println("MR49----------"+ErrorPath);
						 continue;
						 
					 }
		/*------------------------MR49 Endning---------------------------------*/			
		
					
		/*--------------------------MR50----------------------------------*/
					try{
					if (fullPath.equals(Configpath) && category.equals("MR50")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(lValue !=null){
						valueElement.setTextContent("FARMING_COUNTRY_OF_ORIGIN");
						}else{
						valueElement.setTextContent("EU_AGRICULTURE");
						}
						valueElements.get(productId).add(valueElement);
						variantValues.appendChild(valueElement);
						
					} }catch(NullPointerException e){
						 System.out.println("MR50----------"+ErrorPath);
						 continue;						 
					 }
		/*------------------------MR50 Endning---------------------------------*/			
					try{
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("NORIA")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	System.out.println("attrib---"+attrib);
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(attrib.equals("Att_Segment")){
						valueElement.setAttribute("ID", lValue.substring(0,4));
						}else if(attrib.equals("Att_Sup_chain_and_adm_bus_div")){
							valueElement.setAttribute("ID", lValue);
						}
						else{						
						valueElement.setAttribute("ID", lValue);
						}
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else {
							if(!Arrays.toString(proudctonly).contains(attrib) && !Arrays.toString(onlyvariantAttributes).contains(attrib)){
							valuesElement.appendChild(valueElement);
							}
						}
					} }catch(NullPointerException e){
						 System.out.println("NORIA----------"+ErrorPath);
						 continue;						 
					 }
					
					try{
						if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("FLAVOURING")) {
							if (!valueElements.containsKey(productId)) {
								valueElements.put(productId, new ArrayList<>());
							}
							//flavouring = doc.createElement("MultiValue");
							flavouring.setAttribute("AttributeID", attrib);
							Element valueElement = doc.createElement("Value");													
							valueElement.setAttribute("ID", lValue);
							flavouring.appendChild(valueElement);
							valueElements.get(productId).add(valueElement);
							if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
							if (Arrays.toString(variantattributes).contains(attrib)) {
								variantValues.appendChild(flavouring);
							} else {
								valuesElement.appendChild(flavouring);
							}
							}else {
								if(!Arrays.toString(proudctonly).contains(attrib) && !Arrays.toString(onlyvariantAttributes).contains(attrib)){
								valuesElement.appendChild(flavouring);
								}
							}
						} }catch(NullPointerException e){
							 System.out.println("FLAVOURING----------"+ErrorPath);
							 continue;						 
						 }
					
					try{
					if (fullPath.equals(Configpath) && category.equals("LOVV")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}					
						Element valueElement = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						if(attrib.equals("Att_GTIN14")){							
							if(lValue.length()==13){								
							valueElement.setTextContent("0"+lValue);
							}
							else if(lValue.length()==12){
								valueElement.setTextContent("00"+lValue);
								}
							else if(lValue.length()==11){
								valueElement.setTextContent("000"+lValue);
								}
							else if(lValue.length()==10){
								valueElement.setTextContent("0000"+lValue);
								}
							else if(lValue.length()==9){
								valueElement.setTextContent("00000"+lValue);
								}
							else if(lValue.length()==8){
								valueElement.setTextContent("000000"+lValue);
								}
							else{
							valueElement.setTextContent(lValue);	
							}
							}else{
						valueElement.setTextContent(lValue);
						}
						valueElements.get(productId).add(valueElement);
						//valuesElement.appendChild(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else{
							if(!Arrays.toString(proudctonly).contains(attrib)){
							valuesElement.appendChild(valueElement);
							}
						}
					} }catch(NullPointerException e){
						 System.out.println("LOVV----------"+ErrorPath);
						 continue;						 
					 }
					try{
					if (fullPath.equals(Configpath) && category.equals("LOVMD")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	Element mValueGroup = doc.createElement("ValueGroup");
						Element valueElement = doc.createElement("Value");
						//Element valueElement1 = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElement.setTextContent(lValue.substring(0, 10));
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							variantValues.appendChild(valueElement);
						} else {
							valuesElement.appendChild(valueElement);
						}
						}else{
							if(!Arrays.toString(variantattributes).contains(attrib)){
							valuesElement.appendChild(valueElement);
							}
						}
						valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("LOVMD----------"+ErrorPath);
						 continue;
						 
					 }
					try{
					if (fullPath.equals(Configpath) && category.equals("LOVD")) {
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
					//	Element mValueGroup = doc.createElement("ValueGroup");
						Element valueElement = doc.createElement("Value");
					//	Element valueElement1 = doc.createElement("Value");
						valueElement.setAttribute("AttributeID", attrib);
						//if(attrib.equals("Att_Ord_ava_End_Date") || attrib.equals("Att_Ord_ava_Start_Date")|| attrib.equals("Att_Desp_ava_End_Date")){
						valueElement.setAttribute("QualifierID", "AllCountries");
						//}
						
						valueElement.setTextContent(lValue.substring(0, 10));
							//	lValue.substring(0, 10) + " " + lValue.substring(11, 19).replace(".", ":"));
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
							if(attrib.equals("Att_Desp_ava_End_Date") || attrib.equals("Att_Desp_ava_Start_Date")){
							variantValues.appendChild(valueElement);
							}else{
								valuesElement.appendChild(valueElement);
							}
						}else{
							valuesElement.appendChild(valueElement);
						}
						
						valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("LOVD----------"+ErrorPath);
						 continue;						 
					 }
					try{
					if (fullPath.equals(Configpath) && category.equals("LOVLV")) {
						//Element mValueGroup = doc.createElement("ValueGroup");
						
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element valueElement = doc.createElement("Value");
						
						valueElement.setAttribute("AttributeID", attrib);
						valueElement.setAttribute("ID", lValue);
						valueElement.setAttribute("QualifierID", "AllCountries");
						valueElements.get(productId).add(valueElement);
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						if (Arrays.toString(variantattributes).contains(attrib)) {
							
							variantValues.appendChild(valueElement);
						} else {							
							valuesElement.appendChild(valueElement);							
						}
						}else{
							if(!Arrays.toString(proudctonly).contains(attrib) && !Arrays.toString(onlyvariantAttributes).contains(attrib)){									
								//System.out.println("LOVLV----------"+attrib+"productId----"+productId);
								valuesElement.appendChild(valueElement);
								}														
						}
						valueElements.get(productId).add(valueElement);
						valueElements.remove(productId);
					} }catch(NullPointerException e){
						 System.out.println("LOVLV----------"+ErrorPath);
						 continue;						 
					 }

					try{
						if ((fullPath.equals(Configpath) && category.equals("LANGUAGE"))) {	                    
							if (!descriptions.containsKey(productId)) {
								descriptions.put(productId, new LinkedHashMap<>());
							}
							String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																						// surrounding
																// brackets
							
							 String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
						        Pattern pattern = Pattern.compile(regex);
						        Matcher matcher = pattern.matcher(lValue);
						        Element valueGroup = doc.createElement("ValueGroup");
						        Element desvalueGroup = doc.createElement("ValueGroup");
						        valueGroup.setAttribute("AttributeID", attrib);
						        while (matcher.find()) {
						            String language = matcher.group(1); // Extract the language
						         //   System.out.println("language------" + language);
						            String text = matcher.group(2);     // Extract the text
						        //    System.out.println("text------" + text);
						            Element value3 = doc.createElement("Value");
									value3.setAttribute("QualifierID", Language(language));
									value3.setTextContent(text);
									valueGroup.appendChild(value3);									
									}
						        
						       if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){	
						    	 //  System.out.println("Inside ---------"+attrib);
									if (Arrays.toString(variantattributes).contains(attrib) ||Arrays.toString(onlyvariantAttributes).contains(attrib)) {									
										variantValues.appendChild(valueGroup);
									//	System.out.println("Inside TradeITem---------"+attrib);
									} else {
										if(!Arrays.toString(tradeItemOnly).contains(attrib)){
											
										valuesElement.appendChild(valueGroup);
										}
									}
									}else{
										if(!Arrays.toString(onlyvariantAttributes).contains(attrib)){
											
											valuesElement.appendChild(valueGroup);	
										}
									}
	                         
							descriptions.remove(productId);
						}   
					}catch(Exception e){
						
						descriptions.remove(productId);
						continue;
					}
					
					try{
					if ((fullPath.equals(Configpath) && category.equals("LOVL"))) {
                    try{
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																					// surrounding
						//System.out.println("attrib--------"+attrib);									// brackets
						 String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
					        Pattern pattern = Pattern.compile(regex);
					        Matcher matcher = pattern.matcher(lValue);
					        Element valueGroup = doc.createElement("ValueGroup");
					        Element desvalueGroup = doc.createElement("ValueGroup");
					        valueGroup.setAttribute("AttributeID", attrib);
					        Map<String,String> map=new HashMap<>();
					        while (matcher.find()) {
					            String language = matcher.group(1); // Extract the language					            
					            String text = matcher.group(2);     // Extract the text
					        //    System.out.println("text------" + text);
					            Element value3 = doc.createElement("Value");					           
					            if(language.equals("fr")){					            	
		                        	   for(int i=0;i<France.length;i++){	
							            		 Element france = doc.createElement("Value");					            		
							            		france.setAttribute("QualifierID", France[i]);
							            		france.setTextContent(text);
												valueGroup.appendChild(france);
							            	}
		                        	   map.put(language, text);
							            }
							            if(language.equals("de"))
							            	for(int i=0;i<German.length;i++){					            	
							            	{	
							            		 Element german = doc.createElement("Value");					            		
							            		 german.setAttribute("QualifierID", German[i]);
							            		 german.setTextContent(text);
												valueGroup.appendChild(german);
							            	}
							            	map.put(language, text);
							            }
							            if(language.equals("ar")){	
							            		for(int i=0;i<Arabic.length;i++){	
							            		 Element arabic = doc.createElement("Value");					            		
							            		 arabic.setAttribute("QualifierID", Arabic[i]);
							            		 arabic.setTextContent(text);
												valueGroup.appendChild(arabic);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("ch")){
							            		for(int i=0;i<Chinese.length;i++){	
							            		 Element chinese = doc.createElement("Value");					            		
							            		 chinese.setAttribute("QualifierID", Chinese[i]);
							            		 chinese.setTextContent(text);
												valueGroup.appendChild(chinese);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("en")){			
							            		for(int i=0;i<English.length;i++){	
							            		 Element english = doc.createElement("Value");					            		
							            		 english.setAttribute("QualifierID", English[i]);
							            		 english.setTextContent(text);
												valueGroup.appendChild(english);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("es")){				
							            		for(int i=0;i<Spanish.length;i++){	
							            		 Element spanish = doc.createElement("Value");					            		
							            		 spanish.setAttribute("QualifierID", Spanish[i]);
							            		 spanish.setTextContent(text);
												valueGroup.appendChild(spanish);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("it")){
							            		for(int i=0;i<Italian.length;i++){	
							            		 Element italian = doc.createElement("Value");					            		
							            		 italian.setAttribute("QualifierID", Italian[i]);
							            		 italian.setTextContent(text);
												valueGroup.appendChild(italian);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("pt")){
							            		for(int i=0;i<Portuguese.length;i++){	
							            		 Element portuguese = doc.createElement("Value");					            		
							            		 portuguese.setAttribute("QualifierID", Portuguese[i]);
							            		 portuguese.setTextContent(text);
												valueGroup.appendChild(portuguese);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("nl")){		
							            		for(int i=0;i<Dutch.length;i++){	
							            		 Element dutch = doc.createElement("Value");					            		
							            		 dutch.setAttribute("QualifierID", Dutch[i]);
							            		 dutch.setTextContent(text);
												valueGroup.appendChild(dutch);
							            	}
							            		map.put(language, text);
							            }
							            if(language.equals("ro")){		
						            		for(int i=0;i<Ro.length;i++){	
						            		 Element ro = doc.createElement("Value");					            		
						            		 ro.setAttribute("QualifierID", Ro[i]);
						            		 ro.setTextContent(text);
											valueGroup.appendChild(ro);
						            	}
						            		map.put(language, text);
						            }	
							            if(language.equals("ru")){		
						            		for(int i=0;i<Ru.length;i++){	
						            		 Element ru = doc.createElement("Value");					            		
						            		 ru.setAttribute("QualifierID", Ru[i]);
						            		 ru.setTextContent(text);
											valueGroup.appendChild(ru);
						            	}
						            		map.put(language, text);
						            }	
							            if(language.equals("sr")){		
						            		for(int i=0;i<Sr.length;i++){	
						            		 Element sr = doc.createElement("Value");					            		
						            		 sr.setAttribute("QualifierID", Sr[i]);
						            		 sr.setTextContent(text);
											valueGroup.appendChild(sr);
						            	}
						            		map.put(language, text);
						            }	
							            if(language.equals("sv")){		
						            		for(int i=0;i<Sv.length;i++){	
						            		 Element sv = doc.createElement("Value");					            		
						            		 sv.setAttribute("QualifierID", Sv[i]);
						            		 sv.setTextContent(text);
											valueGroup.appendChild(sv);
						            	}
						            		map.put(language, text);
						            }	
							            if(language.equals("hr")){		
						            		for(int i=0;i<Hr.length;i++){	
						            		 Element hr = doc.createElement("Value");					            		
						            		 hr.setAttribute("QualifierID", Hr[i]);
						            		 hr.setTextContent(text);
											valueGroup.appendChild(hr);
						            	}
						            		map.put(language, text);
						            }
							            if(language.equals("ps")){		
						            		for(int i=0;i<Ps.length;i++){	
						            		 Element ps = doc.createElement("Value");					            		
						            		 ps.setAttribute("QualifierID", Ps[i]);
						            		 ps.setTextContent(text);
											valueGroup.appendChild(ps);
						            	}
						            		map.put(language, text);
						            }
							            if(language.equals("sw")){		
						            		for(int i=0;i<Sw.length;i++){	
						            		 Element sw = doc.createElement("Value");					            		
						            		 sw.setAttribute("QualifierID", Sw[i]);
						            		 sw.setTextContent(text);
											valueGroup.appendChild(sw);
						            	}
						            		map.put(language, text);
						            }
					            if(!LanguageCountryCode(language).equals(" ")){
								value3.setAttribute("QualifierID", LanguageCountryCode(language));
								value3.setTextContent(text);
								valueGroup.appendChild(value3);
								map.put(language, text);
					            }
								if(attrib.equals("Att_Inv_name")){		                        	  
		                        	  desvalueGroup.setAttribute("AttributeID", "Att_Short_itm_Dsc");
		  					           Element dscvalue = doc.createElement("Value");		  					           
		  					         if(language.equals("fr")){					            	
			                        	   for(int i=0;i<France.length;i++){	
								            		 Element france = doc.createElement("Value");					            		
								            		france.setAttribute("QualifierID", France[i]);
								            		france.setTextContent(text);
								            		desvalueGroup.appendChild(france);
								            	}
			                        	   map.put(language, text);
								            }
								            if(language.equals("de"))
								            	for(int i=0;i<German.length;i++){					            	
								            	{	
								            		 Element german = doc.createElement("Value");					            		
								            		 german.setAttribute("QualifierID", German[i]);
								            		 german.setTextContent(text);
								            		 desvalueGroup.appendChild(german);
								            	}
								            	map.put(language, text);
								            }
								            if(language.equals("ar")){	
								            		for(int i=0;i<Arabic.length;i++){	
								            		 Element arabic = doc.createElement("Value");					            		
								            		 arabic.setAttribute("QualifierID", Arabic[i]);
								            		 arabic.setTextContent(text);
								            		 desvalueGroup.appendChild(arabic);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("ch")){					            	
								            	
								            		for(int i=0;i<Chinese.length;i++){	
								            		 Element chinese = doc.createElement("Value");					            		
								            		 chinese.setAttribute("QualifierID", Chinese[i]);
								            		 chinese.setTextContent(text);
								            		 desvalueGroup.appendChild(chinese);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("en")){			
								            		for(int i=0;i<English.length;i++){	
								            		 Element english = doc.createElement("Value");					            		
								            		 english.setAttribute("QualifierID", English[i]);
								            		 english.setTextContent(text);
								            		 desvalueGroup.appendChild(english);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("es")){				
								            		for(int i=0;i<Spanish.length;i++){	
								            		 Element spanish = doc.createElement("Value");					            		
								            		 spanish.setAttribute("QualifierID", Spanish[i]);
								            		 spanish.setTextContent(text);
								            		 desvalueGroup.appendChild(spanish);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("it")){
								            		for(int i=0;i<Italian.length;i++){	
								            		 Element italian = doc.createElement("Value");					            		
								            		 italian.setAttribute("QualifierID", Italian[i]);
								            		 italian.setTextContent(text);
								            		 desvalueGroup.appendChild(italian);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("pt")){
								            		for(int i=0;i<Portuguese.length;i++){	
								            		 Element portuguese = doc.createElement("Value");					            		
								            		 portuguese.setAttribute("QualifierID", Portuguese[i]);
								            		 portuguese.setTextContent(text);
								            		 desvalueGroup.appendChild(portuguese);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("nl")){		
								            		for(int i=0;i<Dutch.length;i++){	
								            		 Element dutch = doc.createElement("Value");					            		
								            		 dutch.setAttribute("QualifierID", Dutch[i]);
								            		 dutch.setTextContent(text);
								            		 desvalueGroup.appendChild(dutch);
								            	}
								            		map.put(language, text);
								            }
								            if(language.equals("ro")){		
							            		for(int i=0;i<Ro.length;i++){	
							            		 Element ro = doc.createElement("Value");					            		
							            		 ro.setAttribute("QualifierID", Ro[i]);
							            		 ro.setTextContent(text);
												valueGroup.appendChild(ro);
							            	}
							            		map.put(language, text);
							            }	
								            if(language.equals("ru")){		
							            		for(int i=0;i<Ru.length;i++){	
							            		 Element ru = doc.createElement("Value");					            		
							            		 ru.setAttribute("QualifierID", Ru[i]);
							            		 ru.setTextContent(text);
												valueGroup.appendChild(ru);
							            	}
							            		map.put(language, text);
							            }	
								            if(language.equals("sr")){		
							            		for(int i=0;i<Sr.length;i++){	
							            		 Element sr = doc.createElement("Value");					            		
							            		 sr.setAttribute("QualifierID", Sr[i]);
							            		 sr.setTextContent(text);
												valueGroup.appendChild(sr);
							            	}
							            		map.put(language, text);
							            }	
								            if(language.equals("sv")){		
							            		for(int i=0;i<Sv.length;i++){	
							            		 Element sv = doc.createElement("Value");					            		
							            		 sv.setAttribute("QualifierID", Sv[i]);
							            		 sv.setTextContent(text);
												valueGroup.appendChild(sv);
							            	}
							            		map.put(language, text);
							            }	
								            if(language.equals("hr")){		
							            		for(int i=0;i<Hr.length;i++){	
							            		 Element hr = doc.createElement("Value");					            		
							            		 hr.setAttribute("QualifierID", Hr[i]);
							            		 hr.setTextContent(text);
												valueGroup.appendChild(hr);
							            	}
							            		map.put(language, text);
							            }
								            if(language.equals("ps")){		
							            		for(int i=0;i<Ps.length;i++){	
							            		 Element ps = doc.createElement("Value");					            		
							            		 ps.setAttribute("QualifierID", Ps[i]);
							            		 ps.setTextContent(text);
												valueGroup.appendChild(ps);
							            	}
							            		map.put(language, text);
							            }
								            if(language.equals("sw")){		
							            		for(int i=0;i<Sw.length;i++){	
							            		 Element sw = doc.createElement("Value");					            		
							            		 sw.setAttribute("QualifierID", Sw[i]);
							            		 sw.setTextContent(text);
												valueGroup.appendChild(sw);
							            	}
							            		map.put(language, text);
							            }
								           /* if(map.containsKey("fr")){		
									    		  Element languag= doc.createElement("Value");
									    		  languag.setAttribute("QualifierID", "AllCountries");						    		  
									    		  languag.setTextContent(map.get("fr"));
									    		  desvalueGroup.appendChild(languag);
												//en="FR";
												
									    	  }					     
									    	  else if(map.containsKey("en")){	
									    		  Element languag1= doc.createElement("Value");
									    		  languag1.setAttribute("QualifierID", "AllCountries");						    		  
									    		  languag1.setTextContent(map.get("en"));
									    		  desvalueGroup.appendChild(languag1);
												//break;
									    	  }	
									    	  else{
									    		  for(Map.Entry<String,String> lanug : map.entrySet()){
									    		 Element languag2= doc.createElement("Value");
									    		  languag2.setAttribute("QualifierID", "AllCountries");						    		  
									    		  languag2.setTextContent(lanug.getValue());
									    		  desvalueGroup.appendChild(languag2);
									    		  break;
									    	  }
									    	  }*/

		  								//desvalueGroup.appendChild(dscvalue);
		  								valuesElement.appendChild(desvalueGroup);
		  					        }
					        }
					        if(attrib.equals("Att_Var_Dsc_txt")||attrib.equals("Att_Ing_stat_txt") ){
					        if(map.containsKey("fr")){		
					    		  Element languag= doc.createElement("Value");
					    		  languag.setAttribute("QualifierID", "AllCountries");						    		  
					    		  languag.setTextContent(map.get("fr"));
								valueGroup.appendChild(languag);
								//en="FR";
								
					    	  }					     
					    	  else if(map.containsKey("en")){	
					    		  Element languag1= doc.createElement("Value");
					    		  languag1.setAttribute("QualifierID", "AllCountries");						    		  
					    		  languag1.setTextContent(map.get("en"));
								valueGroup.appendChild(languag1);
								//break;
					    	  }	
					    	  else{					    		  
					    		  for(Entry<String,String> lanug : map.entrySet()){
					    		 Element languag2= doc.createElement("Value");
					    		  languag2.setAttribute("QualifierID", "AllCountries");						    		  
					    		  languag2.setTextContent(lanug.getValue());
								  valueGroup.appendChild(languag2);
					    		  break;
					    	  }
					    	   }
					        } 
					       if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){	
					    	 //  System.out.println("Inside ---------"+attrib);
								if (Arrays.toString(variantattributes).contains(attrib) ||Arrays.toString(onlyvariantAttributes).contains(attrib)) {									
									variantValues.appendChild(valueGroup);
									//System.out.println("Inside TradeITem---------"+attrib);
								} else {
									if(!Arrays.toString(tradeItemOnly).contains(attrib)){
										
									valuesElement.appendChild(valueGroup);
									}
								}
								}
					       else{
									if(!Arrays.toString(onlyvariantAttributes).contains(attrib)){
										
										valuesElement.appendChild(valueGroup);	
									}
								}
                         
						
						descriptions.remove(productId);
						break;
					}catch(Exception e){
						
						descriptions.remove(productId);
						continue;
					}
				} }catch(NullPointerException e){
					 System.out.println("LOVL----------"+ErrorPath);
					 continue;					 
				 }
							  		
			        /*-----------------UOM-------------------------------------*/
			        		try{
			        		if(( fullPath.equals(Configpath) && category.equals("UOM"))){	
			              	   if (!descriptions.containsKey(productId)) {
			                      descriptions.put(productId, new LinkedHashMap<>());
			                  }
			                  String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets			              
			                  String[] entries = descJson.split("},\\s*\\{");
			                  for (String entry1 : entries) {
			                	 
			                    String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");			                 	
			                      String language = partsDesc[0].split(":\\s*")[1].trim();
			                      String text = partsDesc[1].split(":\\s*")[1].trim();			                  
			                      descriptions.get(productId).put(language, text);
			                  }
			                  for (String productID : descriptions.keySet()) {
			                      Map<String, String> descs = descriptions.get(productID);			                     
			                      for (Entry<String, String> entry3 : descs.entrySet()) {
			                    	  netContentumo = doc.createElement("Value");  
			                      	netContentumo.setAttribute("AttributeID",attrib);
			                      	netContentumo.setAttribute("UnitID", unityMeasure(entry3.getValue()));
			                          if(attrib.equals("Att_Net_Wgt") || attrib.equals("Att_Tare")){
			                        	  netContentumo.setAttribute("QualifierID", "AllCountries");
			                          }
			                          netContentumo.setTextContent(entry3.getKey());			                         
			                          if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
						                  if(Arrays.toString(variantattributes).contains(attrib)){
						                	variantValues.appendChild(netContentumo);
						                 }else{
						                  valuesElement.appendChild(netContentumo);
						                	 }
						                  }else{
						                	  valuesElement.appendChild(netContentumo);
						                  }
				                  }    
			                      }
			                     
			                  descriptions.remove(productId);
			                  break;
			              } }catch(NullPointerException e){
								 System.out.println("UOM----------"+ErrorPath);
								 continue;								 
							 }
			/*-----------------------UOM END------------------------------------------------*/
			        		
			     /*--------------------------------NetContentUOM Start--------------------------------*/
			        		if(( fullPath.equals(Configpath) && category.equals("NETCONTENTUOM"))){			        			
			              	   if (!descriptions.containsKey(productId)) {
			                      descriptions.put(productId, new LinkedHashMap<>());
			                  }			             
			                  String descJson = lValue.substring(1, lValue.length() - 2); // Remove surrounding brackets			                 
			                  String[] entries = descJson.split("},\\s*\\{");			                  
			                //  if(entries.length <2){
			                  for (String entry1 : entries) {
			                	  Element  umo = doc.createElement("Value");
			                    String[] partsDesc = entry1.replaceAll("[{}\"]", "").split(",\\s*");
			                    if(!partsDesc[0].trim().equals("volume:")){
			                      String pvalues = partsDesc[0].split(":\\s*")[1].trim();
			                      String unit = partsDesc[1].split(":\\s*")[1].trim();			                      
			                      netUmo.setAttribute("AttributeID",attrib);
			                      umo.setAttribute("QualifierID", "AllCountries");
			                      umo.setAttribute("UnitID", unityMeasure(unit));			                      	  
			                      umo.setTextContent(pvalues);
			                      netUmo.appendChild(umo);
			                      descriptions.get(productId).put(pvalues, unit);
			                      if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
					                  if(Arrays.toString(variantattributes).contains(attrib)){					                	
					                	variantValues.appendChild(netUmo);
					                	 
					                 }else{
					                	valuesElement.appendChild(netUmo);					                	 
					                	 }
					                  }else{					                	 
					                	  valuesElement.appendChild(netContentumo);					                	  
					                  }
			                      }	
			                    }
			                  /*}else{
			                	  for (String entrie : entries) {					                	  
					                    String[] partsDescr = entrie.replaceAll("[{}\"]", "").split(",\\s*");
					                    if(!partsDescr[0].trim().equals("volume:")){
					                      String pvalues = partsDescr[0].split(":\\s*")[1].trim();
					                      String unit = partsDescr[1].split(":\\s*")[1].trim();					                 
					                      Element  netUmo = doc.createElement("Value");
					                      if(unit.equals("KG")){					                      
					                      netUmo.setAttribute("AttributeID",attrib);
					                      netUmo.setAttribute("QualifierID", "AllCountries");
					                      netUmo.setAttribute("UnitID", unityMeasure(unit));			                      	  
					                      netUmo.setTextContent(pvalues);
					                      descriptions.get(productId).put(pvalues, unit);					                      
					                    if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
							                  if(Arrays.toString(variantattributes).contains(attrib)){					                	
							                	variantValues.appendChild(netUmo);							                	 
							                 }else{
							                	valuesElement.appendChild(netUmo);					                	 
							                	 }
							                  }else{					                	 
							                	  valuesElement.appendChild(netUmo);					                	  
							                  }			                    	
					                      }
					                    }
			                  }
			        		}*/
			                  descriptions.remove(productId);
			        		}
			         /*--------------------------------NetContentUOM Start end--------------------------------*/   		
		/*------------------------------MR51-------------------------------------------------*/
			        		try{
			        		if (fullPath.equals(Configpath) && category.equals("MR51TEMP")) {
								Element valueElement = doc.createElement("Value");
								//Element mValueGroup = doc.createElement("ValueGroup");
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}
								if(!product.equals("deliveryToDistributionCenterTemperatureMaximum")){
								//productElement.appendChild(mValueGroup);
								valueElement.setAttribute("AttributeID", attrib);
								valueElement.setAttribute("UnitID", " C");
								valueElement.setAttribute("QualifierID", "AllCountries");
								valueElement.setTextContent(lValue);
								valueElements.get(productId).add(valueElement);
								}else{
									valueElement.setAttribute("AttributeID", attrib);
									valueElement.setTextContent(lValue);									
								}
								 if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if (Arrays.toString(variantattributes).contains(attrib)) {
									variantValues.appendChild(valueElement);
								} else {
									valuesElement.appendChild(valueElement);
								}
								 }else{
									 valuesElement.appendChild(valueElement); 
								 }

								valueElements.remove(productId);
							} }catch(NullPointerException e){
								 System.out.println("MR51TEMP----------"+ErrorPath);
								 continue;								 
							 }
          /*------------------------MR51 End--------------------------------------------------*/  
			        		/*------------------------------MR60-------------------------------------------------*/
			        		try{
			        		if (fullPath.equals(Configpath) && category.equals("MR60TEMP")) {
								Element valueElement = doc.createElement("Value");								
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}								
								valueElement.setAttribute("AttributeID", attrib);								
								valueElement.setTextContent(lValue);
								valueElements.get(productId).add(valueElement);
								if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if (Arrays.toString(variantattributes).contains(attrib)) {
									variantValues.appendChild(valueElement);
								} else {
									if(!Arrays.toString(tradeItemOnly).contains(attrib)){
									valuesElement.appendChild(valueElement);
									}
								}
								}else{
									valuesElement.appendChild(valueElement);
								}
								valueElements.remove(productId);
							} }catch(NullPointerException e){
								 System.out.println("MR60TEMP----------"+ErrorPath);
								 continue;								 
							 }
          /*------------------------MR51 End--------------------------------------------------*/ 
		/*------------------------------MR43-------------------------------------------------*/
			        		try{
			        		if (fullPath.equals(Configpath) && category.equals("MR43")) {			        	
			        			Element multiValueMR43Value = doc.createElement("Value");								
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}
								
								multiValueMR43.setAttribute("AttributeID", attrib);								
								/*if(lValue.equals("CSV01")||lValue.equals("CSV02")|| lValue.equals("CSV09")){
									multiValueMR43Value.setAttribute("ID", "11");	
								}
								else if(lValue.equals("CSV03")||lValue.equals("CSV04")|| lValue.equals("CSV08") || lValue.equals("CSV11")){
									multiValueMR43Value.setAttribute("ID", "12");	
								}else if(lValue.equals("CSV05")||lValue.equals("CSV10")||lValue.equals("CSV12")){
									multiValueMR43Value.setAttribute("ID", "FRO");
								}*/
								multiValueMR43Value.setAttribute("ID",lValue );
								multiValueMR43.appendChild(multiValueMR43Value);
								valueElements.get(productId).add(multiValueMR43Value);
								if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if (Arrays.toString(variantattributes).contains(attrib)) {
									variantValues.appendChild(multiValueMR43);
								} else {
									valuesElement.appendChild(multiValueMR43);
								}
								}else {
									valuesElement.appendChild(multiValueMR43);
								}
								valueElements.remove(productId);
							} }catch(NullPointerException e){
								 System.out.println("MR43----------"+ErrorPath);
								 continue;								 
							 }
          /*------------------------MR43 End--------------------------------------------------*/  
	        		
/*-------------------------------------MR41 Start---------------------*/
			        		/*try{
			        		if (fullPath.equals(Configpath) && category.equals("MR41")) {
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}								
								Element valueElement = doc.createElement("Value");
								valueElement.setAttribute("AttributeID", attrib);								
								if(value.equals("productClassificationDE/categoryCode") && lValue!=null ){
									valueElement.setTextContent("10");
								}else if(value.equals("ECRClassification/categoryCode") && lValue!=null){
									valueElement.setTextContent("20");
								}else if(value.equals("cheeseClassification/categoryCode") && lValue!=null){
									valueElement.setTextContent("54");
								}
																
								valueElements.get(productId).add(valueElement);
								if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if (Arrays.toString(variantattributes).contains(attrib)) {
									variantValues.appendChild(valueElement);
								} else {
									valuesElement.appendChild(valueElement);
								}
								}else{
									valuesElement.appendChild(valueElement);
								}
							} }catch(NullPointerException e){
								 System.out.println("MR41----------"+ErrorPath);
								 continue;
								 }*/
            /*-------------------------------MR41 End -----------------------------------------------*/ 
			        		/*-----------------------PROMOTION-------------------*/
			        		
			        		try{
				        		if (fullPath.equals(Configpath) && category.equals("PROM")) {
				        			Element Promovalue = doc.createElement("Value");
				        			Element promKeyValue = doc.createElement("KeyValue");				        			
				        				PromotionCrossReference.setAttribute("Type", "Rpp_Promotion");
				        				promKeyValue.setAttribute("KeyID", "YODA_Key");
				        				promKeyValue.setTextContent(lValue);
				        				PromotionCrossReference.appendChild(promKeyValue);
			        				productElement.appendChild(PromotionCrossReference);
				        		}
			        		}catch(NullPointerException e){
								 System.out.println("PROMOTION----------"+ErrorPath);
								 continue;
								 }
			        		
           /*--------------------------------MR33 Start------------------------------*/
			        		try{
			        		if (fullPath.equals(Configpath) && category.equals("MR33")) {
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}
								//System.out.println("MR33----------"+attrib);
								Element valueElement = doc.createElement("Value");
								valueElement.setAttribute("AttributeID", attrib);
								if(lValue.equals("EA")){
									valueElement.setAttribute("ID", "BASE_UNIT_OR_EACH");
								}
								else if(lValue.equals("CA")){
									valueElement.setAttribute("ID", "CASE");
								}
								else if(lValue.equals("MX")){
									valueElement.setAttribute("ID", "MIXED_MODULE");
								}
								else if(lValue.equals("PL")){
									valueElement.setAttribute("ID", "PALLET");
								}
								else if(lValue.equals("TL")){
									valueElement.setAttribute("ID", "TRANSPORT_LOAD");
								}
								else if(lValue.equals("DS")){
									valueElement.setAttribute("ID", "DISPLAY_SHIPPER");
								}
								else if(lValue.equals("PK")||((!BaseUnite.equals("false") && !ConsumerUnite.equals("true")))){
									valueElement.setAttribute("ID", "PACK_OR_INNER_PACK");
								}
								valueElements.get(productId).add(valueElement);
								if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if (Arrays.toString(variantattributes).contains(attrib)) {
									variantValues.appendChild(valueElement);
								} else {
									valuesElement.appendChild(valueElement);
								}
								}else{
									valuesElement.appendChild(valueElement);
								}
							} }catch(NullPointerException e){
								 System.out.println("MR33----------"+ErrorPath);
								 continue;								 
							 }

			        		
		   /*--------------------------------MR33 End------------------------------*/
			        		//Element mVGValueGroup = doc.createElement("ValueGroup");
			        		try{
			        		if (fullPath.equals(Configpath) && category.equals("MVG")) {
			        			if("EA".equals(ProdId.get(productId))){
			        			Element valueElement = doc.createElement("Value");
								//Element mValueGroup = doc.createElement("ValueGroup");
								if (!valueElements.containsKey(productId)) {
									valueElements.put(productId, new ArrayList<>());
								}
								if(!lValue.isEmpty()){
								multiValue.setAttribute("AttributeID", attrib);
								productElement.appendChild(multiValue);								
								valueElement.setAttribute("ID","STORAGE_HANDLING");								
								valueElement.setAttribute("QualifierID", "AllCountries");
								valueElements.get(productId).add(valueElement);								
								
								multiValue.appendChild(valueElement);
								valuesElement.appendChild(multiValue);	
								}
								}
				/**	dont modify below four line code. its generating proudct variant**/			
			        			} }catch(NullPointerException e){
									 System.out.println("MVG----------"+ErrorPath);
									 continue;
									 
								 }
			        		productElement.appendChild(valuesElement);
			        		if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
			        		productElement.appendChild(variantElement);
			        		variantElement.appendChild(variantValues);
			        		}
			        		 
                         /**------------------------------Start Att_Num_Of_Serv_rg_Dsc-----------------------------*/
			        		if(fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("NSSize")){			    				
			               		 if (!valueElements.containsKey(productId)) {
			                         valueElements.put(productId, new ArrayList<>());
			                     }			               		 
			               		Element valueElement = doc.createElement("Value");
			                     valueElement.setAttribute("AttributeID", attrib);
			                     valueElement.setAttribute("QualifierID", "AllCountries");
			                     valueElement.setTextContent(lValue.replace(",", "."));
			                     vSSmetaData.appendChild(valueElement);
			                     servingSizeId.appendChild(vSSmetaData);    
			                     valueElements.remove(productId);
			                       }
			        		/**------------------------------End Att_Num_Of_Serv_rg_Dsc-----------------------------*/	
			        		
					try{
						
					if ((fullPath.equals(Configpath) && category.equals("LANGONLY"))) {
						if (!descriptions.containsKey(productId)) {
							descriptions.put(productId, new LinkedHashMap<>());
						}
						String descJson = lValue.substring(1, lValue.length() - 2); // Remove
																					// surrounding
																					// brackets
						
						String regex = "\\{language\\s*:\\s*\"(.*?)\",\\s*text\\s*:\\s*\"(.*?)\"}";
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(lValue);				        
				        Element LGroup = doc.createElement("ValueGroup");
				        LGroup.setAttribute("AttributeID", attrib);
				        Map<String,String> lan = new HashMap<>();				        
				        while (matcher.find()) {
				            String language = matcher.group(1); // Extract the language					            
				            String text = matcher.group(2);
				            Element Lvalue = doc.createElement("Value");								
							Lvalue.setAttribute("QualifierID", Language(language));
							Lvalue.setTextContent(text);
							LGroup.appendChild(Lvalue);
							lan.put(language, text);
				        }
				        if(!lan.containsKey("en")){	
                            if(lan.containsKey("fr")){
                          	  String fValue = lan.get("fr");
                          	 Element Lvalue = doc.createElement("Value");								
    							Lvalue.setAttribute("QualifierID", "en-US");
    							Lvalue.setTextContent(fValue);
    							LGroup.appendChild(Lvalue);
							}else{                                      
						for (Entry<String, String> lang : lan.entrySet()) {
						//	System.out.println("Inside ---------"+lang.getKey());
						if(!"fr".equals(lang.getKey())){
						Element Lvalue = doc.createElement("Value");								
						Lvalue.setAttribute("QualifierID", "en-US");
						Lvalue.setTextContent(lang.getValue());
						LGroup.appendChild(Lvalue);	
						break;
						}
						}	
						}
						}						
						if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){	
					    	 //  System.out.println("Inside ---------"+attrib);
								if (Arrays.toString(variantattributes).contains(attrib) ||Arrays.toString(onlyvariantAttributes).contains(attrib)) {									
									variantValues.appendChild(LGroup);
									//System.out.println("Inside TradeITem---------"+attrib);
								} else {
									if(!Arrays.toString(tradeItemOnly).contains(attrib)){
										
									valuesElement.appendChild(LGroup);
									}
								}
								}
					       else{
									if(!Arrays.toString(onlyvariantAttributes).contains(attrib)){
										
										valuesElement.appendChild(LGroup);	
									}
								}

						descriptions.remove(productId);
						break;
					}
					}catch (Exception e) {
						System.out.println("LANGONLY------> "+ErrorPath);
						System.out.println("Error Records"+e.getMessage()+"---"+e.getStackTrace());
					}
					/*----------------NonNutrientClaims start------------------------*/ 
if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("UCNNCLAIMS")) {
						
						if (!valueElements.containsKey(productId)) {
							valueElements.put(productId, new ArrayList<>());
						}
						Element nonNutrientClaims=doc.createElement("EntityCrossReference");
						Element nNCMetaData = doc.createElement("MetaData");											
						if(attrib.equals("Att_Nut_Clm_Typ")){
							
							nNc =lValue;
						}
						
						if(attrib.equals("Att_Nut_Clm_elt")){							
							if(lValue.equals("LACTOSE")){
								lValue ="LACTOSE_CLM";
							}else if(lValue.equals("ADDITIVES")){
								lValue="ADDITIVES_CLM";
							}else if(lValue.equals("HONEY")){
								lValue="HONEY_CLM";
							}	
							
							nonNutrientClaims.setAttribute("EntityID", lValue);
							nonNutrientClaims.setAttribute("Type", "Rpe_NonNutrientClaims");
							Element nNConstant = doc.createElement("Value");
							nNConstant.setAttribute("AttributeID", "Att_Clm_Typ_Dis_on_Pack");
							nNConstant.setAttribute("QualifierID", "AllCountries");
							
							
							Element nNCValue = doc.createElement("Value");						
							nNCValue.setAttribute("AttributeID", "Att_Nut_Clm_Typ");
							//nNCValue.setAttribute("QualifierID", "AllCountries");
							if(nNc!=null){
							nNConstant.setAttribute("ID", "Y");
							nNCMetaData.appendChild(nNConstant);
							nNCValue.setAttribute("ID", nNc);						
							nNCMetaData.appendChild(nNCValue);
							}
							
							nonNutrientClaims.appendChild(nNCMetaData);
							if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								variantElement.appendChild(nonNutrientClaims);
							}
							 }
											
					}
					
					
					
					/*---- Product_Variant level: POLYPHOSPHATE re-classified as Non-Nutrient claim ----*/
					if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("MR17")) {
					    if(fullPath.contains("nutritionalClaimType") && attrib.equals("Att_Nut_Clm_Typ")){
					        nNc = lValue;
					    }
					    if(fullPath.contains("nutritionalClaimNutrientElement") && attrib.equals("Att_Nut_Clm_elt") && "POLYPHOSPHATE".equals(lValue)){
					       
					        if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
					            variantElement.appendChild(getVaraintPolyphosphate(doc,lValue,nNc));
					        }
					    }
					}
					
					
					try{
	                   	 if (fullPath.replaceAll("#\\d+","").equals(Configpath) && category.equals("ANIMALCODE")) { 
	                   		Element aCode = doc.createElement("Value");
	 								animalCode.setAttribute("AttributeID", attrib);
	 								aCode.setAttribute("ID", lValue);
	 								animalCode.appendChild(aCode);  
	 								if("EA".equals(ProdId.get(productId))|| ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
	 									variantValues.appendChild(animalCode);
	 									}
	 								 
	                        		 }	          							
	         							
	                   	 }catch(NullPointerException e){
	                       	 System.out.println("ANIMALCODE Error----------"+ErrorPath);
	   						 System.out.println("ANIMALCODE Path----------"+e.getMessage());
	   						 continue;
	                        }
					
				}
				applicablemarket.clear();
			}
		}catch(Exception e){
			System.out.println("Prodcut Error Records Product and TradeItems"+ErrorPath);
			System.out.println("Error Records"+e.getMessage()+"---"+e.getStackTrace());
		//	logger.error("UC or UL Error Records-----"+ErrorPath);
			continue;
			
		}
		}
	/*-----------------print UC/UL once again----------------------------------*/
		
	/*	for (Map.Entry<String, List<String[]>> entry : dataMap.entrySet()) {
			String productId = entry.getKey();
			List<String[]> productDetails = entry.getValue();
			String ErrorPath = null;						
			String UserTypeID = userTypeID(ProdId.get(productId));
			String ConsumerUnite =pKCUnite.get(productId);
	  		  String BaseUnite =pKBUnite.get(productId);
					if (!UserTypeID.equals("Variant") && !UserTypeID.equals("")) {
						additionalproductElement = doc.createElement("Product");
						Element YODAKey = doc.createElement("KeyValue");
						PID=ProdId.get(productId);			
						if("GRA02".contentEquals(grade.get(productId)) && "EA".equals(PID)){	
							
							additionalproductElement.setAttribute("UserTypeID", UserTypeID+"_SC");
						}else{
							if(UserTypeID.equals("Savencia_Pack")&&((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){					
								additionalproductElement.setAttribute("UserTypeID", "Product");
							}else{								
								additionalproductElement.setAttribute("UserTypeID", UserTypeID);
							}
						}
						if (UserTypeID.equals("Savencia_Case")
								|| UserTypeID.equals("Savencia_Pallet") || UserTypeID.equals("Savencia_Display_Shipper")
								|| UserTypeID.equals("Savencia_Mixed_Module") || UserTypeID.equals("Savencia_Transport_Load")) {
						if(!BaseUnite.equals("false") && !ConsumerUnite.equals("true")){
							UserTypeID.equals("Savencia_Pack");
						}
							additionalproductElement.setAttribute("ParentID", "Trade_Item_Root");

						}
						YODAKey.setAttribute("KeyID","YODA_Key");
						YODAKey.setTextContent(productId);
						additionalproductElement.appendChild(YODAKey);
					}
						if (UserTypeID.equals("Product") || (BaseUnite.equals("false") && ConsumerUnite.equals("true"))) {
							AddvariantElement = doc.createElement("Product");
							Element vkeyvalue = doc.createElement("KeyValue");														
							if("GRA02".contentEquals(grade.get(productId))){
								AddvariantElement.setAttribute("UserTypeID", "Product_Variant_SC");
							}else{							
								AddvariantElement.setAttribute("UserTypeID", "Product_Variant");
							}			
							vkeyvalue.setAttribute("KeyID", "Variant_Key");
							vkeyvalue.setTextContent(productId+"001");
							AddvariantElement.appendChild(vkeyvalue);
							additionalproductElement.appendChild(AddvariantElement);
						}
					//	productsElement.appendChild(variantElement);
						String proudctdisplayid= null;
						String RpcProductGTLink =null;
						for (String[] detail : productDetails) {
							String group = detail[0];
							String product = detail[1];
							String value = detail[2];
							String lValue = detail[3];
							ErrorPath = group + "|"+productId+"|" + product + "|" + value +"|"+lValue;							
							for (String[] values : att) {
								entity = values[0]; // prod/var/package
								category = values[1];
								Configpath = values[2];
								attrib = values[3];
								try{
								if("EA".equals(ProdId.get(productId)) || ((BaseUnite.equals("false") && ConsumerUnite.equals("true")))){
								if(value.equals("categorieProduit")){
									if(lValue.equals("CATP01")){
										proudctdisplayid="PRD_MASS_CONS";
										//productElement.setAttribute("ProuctID", lValue);						
									}else if(lValue.equals("CATP02")){
										proudctdisplayid="PRD_INDUS_PRD";
									}else if(lValue.equals("CATP03")){
										proudctdisplayid="PRD_FOOD_SERV_SOL";
									}else if(lValue.equals("CATP04")){
										proudctdisplayid="PRD_FOOD_SERV_CAT";
									}else if(lValue.equals("CATP05")){
										proudctdisplayid="PRD_INT_INV_PRD";
									}
								
								}
								
								if(value.equals("groupTechnology") && attrib.equals("Rpc_Product_GT_Link")){
										RpcProductGTLink=proudctdisplayid+pID(lValue);
										additionalproductElement.setAttribute("ParentID", RpcProductGTLink);									
									}
								
								} }catch(NullPointerException e){
									 System.out.println("partentID----------"+ErrorPath);
									 continue;
									 
								 }
								
								}
								}						
					
					productsElement.appendChild(additionalproductElement);
					
			}doc
*/
	return doc;
	}
	/*private static void recursive(
 		   String market,
 		   Set<String> description, 
 		   Map<String, Set<String>> targetMarket){
		if(targetMarket.containsKey(market)){
			description.addAll(targetMarket.get(market));
		}
 	   for(String child :targetMarket.keySet() ){
 		   if(child.equals(market))
 		   continue;
 		  recursive(market,description,targetMarket);
 	   }
 	   
    }*/
	/*-----------MArket link--------------*/
	/*private static void collectiocollectionDescriptionnDescription(String id, Map<String,Set<String>> tradeitemDesc,
			                                Map<String,String> tradeparent,Set<String> alDescription){
		Set<String> description = tradeitemDesc.get(id);
		System.out.println("tradeitemDesc-----"+tradeitemDesc.get(id));
		if(description !=null){
			alDescription.addAll(description);
		}
		System.out.println("tradeparent-----"+tradeparent.entrySet());
		System.out.println("id-----"+id);
		for(Map.Entry<String, String> entry : tradeparent.entrySet()){
			System.out.println("entry -----"+entry);
			if(entry.equals(id)){
				System.out.println("entry inside-----"+entry);	
				collectionDescription(entry.getKey(),tradeitemDesc,tradeparent,alDescription);
			}
		}
	}*/		

	private static void writeXMLToFile(Document doc, String filePath) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		//transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");		
		//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "UTF-8");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult result = new StreamResult(new File(filePath));

		transformer.transform(source, result);
	//	logger.info("---------STEP XML Logger Ending----------");
	}
	

	private static String LanguageCountryCode(String attribute) {
		switch (attribute) {  
	     case "bu":
	        return "bu-BU";
	    case "ca":
	        return "ca-CA";
	 	 case "cz":
	        return "cz-CZ";
	 	case "cs":
	        return "cs-CZ";
	    case "da":
	        return "da-DU";
	   	case "el":
	        return "el-GR";
	    case "hi":
	        return "hi-IN";
	    case "hu":
	        return "hu-HU";
	    case "il":
	        return "it-IT";
	    case "ja":
	        return "ja-JP";
	    case "st":
	        return "st-LS";
	   	 case "ms":
	        return "ms-MY";
	    case "pl":
	        return "pl-PL";	   
	    case "sk":
	        return "sk-SK";	    
	    case "th":
	        return "th-TH";
	    case "uk":
	        return "uk-UA";
	    case "fi":
	        return "fi-FI";
	    case "ku":
	        return "ku-IQ";
	    case "he":
	        return "he-IL";
	    case "lb":
	        return "lb-LU";
	    case "mi":
	        return "mi-NZ";
	    case "az":
	        return "az-AZ";
	    case "bn":
	        return "bn-BD";
	    case "bs":
	        return "bs-BA";
	    case "et":
	        return "et-EE";
	    case "ka":
	        return "ka-GE";
	    case "ko":
	        return "ko-KP";
	    case "kk":
	        return "kk-KZ";
	    case "lv":
	        return "lv-LV";
	    case "mk":
	        return "mk-MK";
	    case "si":
	        return "si-LK";
	    case "sl":
	        return "sl-SI";
	    case "sq":
	        return "sq-AL";
	    case "tr":
	        return "tr-CY";
	    case "ta":
	        return "ta-LK";
	    case "bh":
	        return "ar-AE";	    
	    case "cy":
	        return "el-GR";
	    case "hr":
	        return "hr-BA";
	    case "gh":
	        return "en-US";
	    case "kz":
	        return "ru-RU";
	    case "ml":
	        return "fr-FR";
	    case "mr":
	        return "ar-AE";
	    case "md":
	        return "ro-RO";	    
	    case "ye":
	        return "ar-AE";
	    case "mu":
	        return "en-MU";
	    case "vn":
	        return "vi-VN";
	    case "sr":
	        return "nl-SR";
	    case "sy":
	        return "ar-AE";
	    case "ht":
	        return "ht-HT";
	    case "jo":
	        return "ar-AE";
	    case "cu":
	        return "es-CU";
	    case "mt":
	        return "en-MT";
	    case "za":
	        return "en-ZA";
	    case "om":
	        return "ar-OM";
	    case "CU":
	        return "es-CU";
	    case "MT":
	        return "mt-MT";
	    case "uz":
	        return "uz-UZ";
	    case "hy":
	        return "hy-AM";
	    case "dz":
	        return "dz-BT";
	    case "be":
	        return "be-BY";
	    case "my":
	        return "my-MM";
	    case "qu":
	        return "qu-BO";
	    case "ay":
	        return "ay-BO";
	    case "bg":
	        return "bu-BU";
	    case "rn":
	        return "rn-BI";
	    case "km":
	        return "km-KH";	    
	    case "ti":
	        return "ti-ER";
	    case "ss":
	        return "ss-SZ";
	    case "am":
	        return "am-ET";
	    case "id":
	        return "id-ID";
	    case "fa":
	        return "fa-IR";
	    case "ga":
	        return "ga-IE";
	    case "is":
	        return "is-IS";
	    case "ky":
	        return "ky-KG";
	    case "lo":
	        return "lo-LA";
	    case "mg":
	        return "mg-MG";
	    case "ny":
	        return "ny-MW";
	    case "dv":
	        return "dv-MV";
	    case "mn":
	        return "mn-MN";
	    case "ne":
	        return "ne-NP";
	    case "ur":
	        return "ur-PK";
	    case "ps":
	        return "ps-AF";
	    case "gn":
	        return "gn-PY";
	    case "sg":
	        return "sg-CF";
	    case "rw":
	        return "rw-RW";
	    case "sm":
	        return "sm-WS";
	    case "zh":
	        return "zh-SG";
	    case "so":
	        return "so-SO";
	    case "rm":
	        return "rm-CH";
	    case "tj":
	        return "tj-TJ";
	    case "to":
	        return "to-TO";
	    case "tk":
	        return "tk-TM";
	    case "bi":
	        return "bi-VU";
	    case "la":
	        return "la-VA";
	    case "sn":
	        return "sn-ZW";
	    case "nd":
	        return "nd-ZW";
	    	    default:
	        return " ";
}
	}
	private static String Language(String attribute) {
		switch (attribute) {  
		case "fr":
	        return "fr-FR";
	    case "en":
	        return "en-US";
	    case "ar":
	        return "ar-AE";
	    case "bu":
	        return "bu-BU";
	    case "ca":
	        return "ca-CA";
	    case "ch":
	        return "ch-CH";
	    case "cz":
	        return "cz-CZ";
	    case "cs":
	        return "cs-CZ";
	    case "da":
	        return "da-DU";
	    case "nl":
	        return "nl-NL";
	    case "de":
	        return "de-DE";
	    case "el":
	        return "el-GR";
	    case "hi":
	        return "hi-IN";
	    case "hu":
	        return "hu-HU";
	    case "il":
	        return "it-IT";
	    case "ja":
	        return "ja-JP";
	    case "st":
	        return "st-LS";
	    case "it":
	        return "lt-LT";
	    case "ms":
	        return "ms-MY";
	    case "pl":
	        return "pl-PL";
	    case "pt":
	        return "pt-PT";
	    case "ro":
	        return "ro-RO";
	    case "ru":
	        return "ru-RU";
	    case "sr":
	        return "sr-RS";
	    case "sk":
	        return "sk-SK";
	    case "es":
	        return "es-ES";
	    case "sv":
	        return "sv-SE";
	    case "th":
	        return "th-TH";
	    case "uk":
	        return "uk-UA";
	    case "fi":
	        return "fi-FI";
	    case "ku":
	        return "ku-IQ";
	    case "he":
	        return "he-IL";
	    case "lb":
	        return "lb-LU";
	    case "mi":
	        return "mi-NZ";
	    case "az":
	        return "az-AZ";
	    case "bn":
	        return "bn-BD";
	    case "bs":
	        return "bs-BA";
	    case "et":
	        return "et-EE";
	    case "ka":
	        return "ka-GE";
	    case "ko":
	        return "ko-KP";
	    case "kk":
	        return "kk-KZ";
	    case "lv":
	        return "lv-LV";
	    case "mk":
	        return "mk-MK";
	    case "si":
	        return "si-LK";
	    case "sl":
	        return "sl-SI";
	    case "sq":
	        return "sq-AL";
	    case "tr":
	        return "tr-CY";
	    case "ta":
	        return "ta-LK";	    
	    case "bh":
	        return "ar-AE";
	    case "ba":
	        return "sr-RS";
	    case "cy":
	        return "el-GR";
	    case "hr":
	        return "hr-BA";
	    case "gh":
	        return "en-US";
	    case "kz":
	        return "ru-RU";
	    case "ml":
	        return "fr-FR";
	    case "mr":
	        return "ar-AE";
	    case "md":
	        return "ro-RO";	    
	    case "ye":
	        return "ar-AE";
	    case "mu":
	        return "en-MU";
	    case "vn":
	        return "vi-VN";	   
	    case "sy":
	        return "ar-AE";
	    case "ht":
	        return "ht-HT";
	    case "jo":
	        return "ar-AE";
	    case "cu":
	        return "es-CU";
	    case "mt":
	        return "en-MT";
	    case "za":
	        return "en-ZA";
	    case "om":
	        return "ar-OM";
	    case "CU":
	        return "es-CU";
	    case "MT":
	        return "mt-MT";
	    case "uz":
	        return "uz-UZ";
		    case "hy":
		        return "hy-AM";
		    case "dz":
		        return "dz-BT";
		    case "be":
		        return "be-BY";
		    case "my":
		        return "my-MM";
		    case "qu":
		        return "qu-BO";
		    case "ay":
		        return "ay-BO";
		    case "bg":
		        return "bu-BU";
		    case "rn":
		        return "rn-BI";
		    case "km":
		        return "km-KH";	    
		    case "ti":
		        return "ti-ER";
		    case "ss":
		        return "ss-SZ";
		    case "am":
		        return "am-ET";
		    case "id":
		        return "id-ID";
		    case "fa":
		        return "fa-IR";
		    case "ga":
		        return "ga-IE";
		    case "is":
		        return "is-IS";
		    case "ky":
		        return "ky-KG";
		    case "lo":
		        return "lo-LA";
		    case "mg":
		        return "mg-MG";
		    case "ny":
		        return "ny-MW";
		    case "dv":
		        return "dv-MV";
		    case "mn":
		        return "mn-MN";
		    case "ne":
		        return "ne-NP";
		    case "ur":
		        return "ur-PK";
		    case "ps":
		        return "ps-AF";
		    case "gn":
		        return "gn-PY";
		    case "sg":
		        return "sg-CF";
		    case "rw":
		        return "rw-RW";
		    case "sm":
		        return "sm-WS";
		    case "zh":
		        return "zh-SG";
		    case "so":
		        return "so-SO";
		    case "rm":
		        return "rm-CH";
		    case "tj":
		        return "tj-TJ";
		    case "to":
		        return "to-TO";
		    case "tk":
		        return "tk-TM";
		    case "bi":
		        return "bi-VU";
		    case "la":
		        return "la-VA";
		    case "sn":
		        return "sn-ZW";
		    case "nd":
		        return "nd-ZW";
		    	    default:
		        return " ";
}
	}

	/*private static String context(String attribute){		
		switch (attribute) {	
	    case "ar_AE" :
			return "19";
	    case "ar_EG":
	    	return "37";	    
	    case "ar_KW":
	    	return "47";
	    case "ar_MA":
	    	return "40";
	    case "ar_SA":
	    	return "31";
	    case "ar_TN":
	    	return "55";
	    case "bu_BG":	
	    	return "62";
	    case "ca_CN":
	    	return "06";
	    case "ch_HK":
	    	return "44";
	    case "ch_SG":
	    	return "15";
	    case "da_DK":
	    	return "41";
	    case "de_AT":
	    	return "33";
	    case "de_BE":
	    	return "09";
	    case "de_CH":
	    	return "27";
	    case "de_DE":
	    	return "04";
	    case "de_NG":
	       return "54";
	    case "el_GR":
	    	return "36";
	    case"en_AU":
	    	return "61";
	    case "en_CA":
	    	return "23";
	    case "en_GB":
	    	return "16";
	    case "en_HK":
	    	return "50";
	    case "en_ID":
	    	return "22";	    		
	    case "en_IE":
	    	return "25";
	    case "en_IN":
	    	return "21";
	    case "en_NG":
	    	return "52";
	    case "en_PH":
	    	return "46";
	    case "en_SG":
	    	return "43";
	    case "en_US":
	    	return "03";
	    case "es_AR":
	    	return "02";
	    case "es_CL":
	    	return "34";
	    case "es_DO":
	    	return "48";
	    case "es_ES":
	    	return "07";
	    case "es_MX":
	    	return "60";
	    case "es_UY":
	    	return "35";
	    case "fr_BE":
	    	return "08";
	    case "fr_CA":
	    	return "24";
	    case "fr_CG":
	    	return "57";
	    case "fr_CH":
	    	return "26";
	    case "fr_CI":
	    	return "42";
	    case "fr_FR":
	    	return "01";
	    case "fr_GA":
	    	return "56";
	    case "fr_NG":
	    	return "53";
	    case "fr_RE":
	    	return "58";
	    case "hi_IN":
	    	return "20";
	    case "hu_HU":
	    	return "30";
	    case "it_CH":
	    	return "28";
	    case "it_IT":
	        return "11";
	    case "ja_JP":
	    	return "29";
	    case "ko_KR":
	    	return "14";
	    case "lt_LT":
	    	return "59";
	    case "ms_MY":
	    	return "45";
	    case "nl_BE":
	    	return "64";
	    case "nl_NL":
	    	return "63";
	    case "pl_PL":
	    	return "13";
	    case "pt_BR":
	    	return "05";
	    case "pt_PT":
	    	return "38";
	    case "ro_RO":
	    	return "12";
	    case "ru_RU":
	    	return "10";
	    case "sk_SK":
	    	return "18";
	    case "sr_RS":
	    	return "32";
	    case "sv_SE":
	    	return "51";
	    case "th_TH":
	    	return "39";
	    case "uk_UA":
	    	return "17";
	    case "cs_CZ":
	    	return "69";
	    case "ar_DZ":
	    	return "65";
	    case "ar_IL":
	    	return "66";
	    case "ar_IQ":
	    	return "67";
	    case "ar_LY":
	    	return "68";
	    case "de_LU":
	    	return "70";
	    case "en_CN":
	    	return "84";
	    case "en_NZ":
	    	return "72";
	    case "en_TW":
	    	return "85";
	    case "fi_FI":
	    	return "73";
	    case "fr_LU":
	    	return "74";
	    case "fr_MF":
	    	return "86";
	    case "fr_NC":
	    	return "87";
	    case "fr_PF":
	    	return "88";
	    case "he_IL":
	    	return "75";
	    case "ku_IQ":
	    	return "76";
	    case "lb_LU":
	    	return "77";
	    case "mi_NZ":
	    	return "78";
	    case "pt_AO":
	    	return "79";
	    case "sv_FI":
	    	return "80";	    
	    case "zh_CN":
	    	return "83";
	    case "zh_TW":
	    	return "81";
	    default:
	        return " ";
	    }
	} */
private static String userTypeID(String attribute) {
		switch (attribute) {
		case "EA":
			return "Product";
		case "PK":
			return "Savencia_Pack";
		case "CA":
			return "Savencia_Case";
		case "DS":
			return "Savencia_Display_Shipper";
		case "PL":
			return "Savencia_Pallet";
		case "MX":
			return "Savencia_Mixed_Module";
		case "TL":
			return "Savencia_Transport_Load";
		case "VRI":
			return "Product_Variant";
		case "REC":
			return "Recipe";
		case "PACK":
			return "Packaging";
		case "NUTR":
			return "Nutrient";
		case "PACK_SUB":
			return "PackagingSubType";
		case "composit":
			return "Product";

		default:
			return "";

		}
	}

	private static String pID(String id) {
		switch (id) {
		case "TECHN11":case "TECHN10":case "TECHN13":case "TECHN14":case "TECHN15":case "TECHN16":case "TECHN17":
		case "TECHN18":case "TECHN24":case "TECHN23":case "TECHN25":case "TECHN20":case "TECHN21":case "TECHN22":
			case "TECHN19":case "TECHN12":
			return "BUT_CREAM";
		case "TECHN01":case "TECHN02":case "TECHN03":case "TECHN04":case "TECHN05":case "TECHN06":case "TECHN07":
		case "TECHN08":case "TECHN09":case "TECHN61":
			return "CHEES_PRD";
		case "TECHN54":
		    return "CHOCO";
		case "TECHN57":case "TECHN58":case "TECHN59": 
			return "DELICAT";
		case "TECHN50":case "TECHN46":case "TECHN48":case "TECHN47":case "TECHN49":case "TECHN51":case "TECHN53":
		case "TECHN52":case "TECHN62": 
			return "OTH_MLK_PRD";
		case "TECHN26":case "TECHN27":case "TECHN31":case "TECHN29":case "TECHN30":case "TECHN32":case "TECHN35":
		case "TECHN42":case "TECHN43":case "TECHN36":case "TECHN39":case "TECHN37":case "TECHN40":case "TECHN41":
		case "TECHN38":case "TECHN44":case "TECHN45":case "TECHN28":case "TECHN34":
		case "TECHN33":
			return "POW_ING";
		case "TECHN55":case "TECHN56":
			return"SEAF_PROD";
		case "TECHN60":case "TECHN98":
			return "OTHERS"; 
		default:
			return "TRANS_PRD";

		}
	}

	
	private static String siteCode(String attribute) {
		switch (attribute) {
		case "Att_Pack_site_cod":
			return "MAN_";
		case "Att_Sel_ent_cod":
			return "SEL_";
		default:
			return "CLS_";

		}
	}

	private static boolean isSiteCodeAttribute(String attribute) {
		HashSet<String> siteCode = new HashSet<>();
		boolean value = false;
		siteCode.add("Att_Main_Pack_site");
		siteCode.add("Att_Pack_site_Name");
		siteCode.add("Att_Prd_site");
		siteCode.add("Att_Sel_ent_cod");
		if (siteCode.contains(attribute)) {
			value = true;
		}
		return value;

	}

	private static String unityMeasure(String attribute) {
		switch (attribute) {
		case "ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âµm":
			return "unece.unit.4H";
		case "ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â¢":
			return "unece.unit.A11";
		case "CM":
			return "unece.unit.CMT";
		case "FT":
			return "unece.unit.FOT";
		case "IN":
			return "unece.unit.INH";
		case "KM":
			return "unece.unit.KTM";
		case "M":
			return "unece.unit.MTR";
		case "MILE":
			return "unece.unit.SMI";
		case "MM":
			return "unece.unit.MMT";
		case "NM":
			return "unece.unit.C45";
		case "YD":
			return "unece.unit.YRD";
		case "LM":
			return "unece.unit.LUM";
		case "ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âµg":
			return "unece.unit.MC";
		case "GR":
			return "unece.unit.GRM";
		case "KG":
			return "unece.unit.KGM";
		case "LB":
			return "unece.unit.LBR";
		case "MG":
			return "unece.unit.MGM";
		case "OZ":
			return "unece.unit.ONZ";
		case "T":
			return "unece.unit.TNE";
		case "ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â°C":
			return "unece.unit.CEL";
		case "ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â°F":
			return "unece.unit.FAH";
		case "MQ":
			return "unece.unit.MTQ";
		case "CFU":
		    return "unece.unit.CFU";
		case "MPN":
		    return "unece.unit.MPN";
		case "ML":
		    return "unece.unit.MLT";
		case "G":
		    return "unece.unit.GRM";
		case "ENERC":
			return "unece.unit.KCAL";
		case "ENER-":
			return "unece.unit.KJ";
		case "SUGAR-":
			return "unece.unit.GRM";
		case "CHOAVL":
			return "unece.unit.GRM";
		case "FASAT":
			return "unece.unit.GRM";
		case "FAT":
			return "unece.unit.GRM";
		case "PRO-":
			return "unece.unit.GRM";
		case "SALTEQ":
			return "unece.unit.GRM";
		case "LT":
			return "unece.unit.LTR";
		case "PC":
			return "Piece";
		default:
			return "";
		}
	}
	private static String commerialfamily(String attribute) {
		switch (attribute) {
		case "CPR01":
			return "PRODUIT A MARQUE";
		case "CPR02":
			return "MDD ET ASSIMILES";
		case "CPR03":
			return "B2B";
		case "CPR04":
			return "BPP CATEGORIES";
		case "CPR05":
			return "AUTRE";
		case "CPR06":
			return "DECLASSE";
		case "ADEFINIR":
			return "A DEFINIR";
		default:
			return " ";
		}
	}
	private static String emballage_type(String attribute) {
		switch (attribute) {
		case "0":case "3":case "8":case "24":case "23":case "31":case "45":case "12":case "26":case "35":case "27":case "32":case "33":case "42":case "36":case "44":		
			return "OTHER";
		case "7":
			return "DIVIDER_PROTECTOR";
		case "5":case "14":
			return "LABEL";
		case "11":case "6":case "10":case "15":case "22":case "16":case "2":case "40":case "34":case "21":case "17":case "41":case "39":case "19":case "43":
			return "MAIN_PACKAGE_TYPE";
		case "9":case "4":
			return "LID";
		case "18":
			return "CAP";
		case "1":
			return "ABSORBING_BLOTTER";
		case "20":
			return "SLEEVE";
		case "25":
			return "CONSUMPTION_UTENSIL";
		case "13":
			return "BASE";
		default:
				return "";
			}
		}
			
	private static boolean packagingSubType(String attribute) {

		HashSet<String> varient = new HashSet<>();
		boolean value = false;
		varient.add("Att_Reus_Pack");
		varient.add("Att_Com_Pack");
		varient.add("Att_Pack");
		varient.add("Att_Rec_Pack");
		varient.add("Att_Dang_sub_In_Pack");
		varient.add("Att_Per_rec_mat");
		varient.add("Att_Per_Com_%");
		varient.add("Att_Adt_pdt_clas_val");
		varient.add("Att_sgl_use_plas_sup_ind");
		varient.add("Att_Reus_Pack_ind");
		varient.add("Att_Pack_Feat_Consumer");
		varient.add("Att_Pack_typ_desc");
		varient.add("Att_Pack_mat_elt");
		varient.add("emballage/compositeMaterial_quantity");
		varient.add("emballage/compositeMaterial_code");
		varient.add("emballage/compositeMaterial_packagingMaterialThickness");
		varient.add("emballage/compositeMaterial_packagingMaterialClassificationCodeReference");
		varient.add("emballage/compositeMaterial_packagingMaterialColourCodeReference");
		varient.add("emballage/compositeMaterial_packagingRawMaterialInformation/packagingRawMaterialCode");
		varient.add("emballage/compositeMaterial_packagingRawMaterialInformation/packagingRawMaterialContentPercentage");
		varient.add("emballage_type");
		varient.add("emballage_");
		varient.add("emballage_composant");
		varient.add("emballage_poids/valeur");
		varient.add("emballage_packagingMaterialColourCodeReference");
		varient.add("palette_type");
		varient.add("palette_composant");
		varient.add("palette_");
		varient.add("Att_Main_Pack_Typ");
		varient.add("Att_Reus_plt_sup_ind");
		varient.add("Att_plt_sup_Typ");
		varient.add("emballage_packagingMaterialClassificationCodeReference");
		varient.add("emballage_packagingRawMaterialInformation/packagingRawMaterialCode");
		varient.add("emballage_packagingRawMaterialInformation/packagingRawMaterialContentPercentage");
		if (varient.contains(attribute)) {
			value = true;
		}
		return value;

	}

		private static boolean recipeAttributes(String attribute) {
		HashSet<String> recipe = new HashSet<>();
		boolean value = false;
		recipe.add("ingredientStatement_");
		recipe.add("allergenStatement_");		
		recipe.add("size_height");
		recipe.add("onPackFatPercentageInDryMatter_fatPercentageInDryMatterMeasurementPrecisionCode");
		recipe.add("physico_criteresObli/grasSurSec");
		recipe.add("allergenStatement");
		/*
		 * recipe.add("typeAnalyse/tauxSel/mini");
		 * recipe.add("mustTradeItemBeChilled");
		 * recipe.add("mustTradeItemBeChilled");
		 * recipe.add("transformedweightUsedPerUC");
		 */
		recipe.add("fab_etapes");
		recipe.add("nom_nom");
		recipe.add("composition_isPrepared");
		recipe.add("infoConso_additionnelles/isRawMaterialIrradiated");
		recipe.add("fab_code");

		recipe.add("infoConso_allergenes/01");
		recipe.add("infoConso_allergenes/02");
		recipe.add("infoConso_allergenes/03");
		recipe.add("infoConso_allergenes/04");
		recipe.add("infoConso_allergenes/05");
		recipe.add("infoConso_allergenes/06");
		recipe.add("infoConso_allergenes/07");
		recipe.add("infoConso_allergenes/08");
		recipe.add("infoConso_allergenes/09");
		recipe.add("infoConso_allergenes/10");
		recipe.add("infoConso_allergenes/11");
		recipe.add("infoConso_allergenes/12");
		recipe.add("infoConso_allergenes/13");
		recipe.add("infoConso_allergenes/14");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismCode");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismAnalysisMethod");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismBasisValue");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismBasisMeasurementUnitCode");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismMaximumValue");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismWarningValue");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismMeasurementUnitCode");
		recipe.add("processMicrobiologicalInformation_microbiologicalOrganismMaximumValuePrecisionCode");		
		recipe.add("microbiologicalInformation_microbiologicalOrganismCode");
		recipe.add("microbiologicalInformation_microbiologicalOrganismAnalysisMethod");
		recipe.add("microbiologicalInformation_microbiologicalOrganismBasisValue");
		recipe.add("microbiologicalInformation_microbiologicalOrganismBasisMeasurementUnitCode");
		recipe.add("microbiologicalInformation_microbiologicalOrganismMaximumValuePrecisionCode");
		recipe.add("microbiologicalInformation_microbiologicalOrganismMeasurementUnitCode");
		recipe.add("microbiologicalInformation_microbiologicalOrganismMaximumValue");
		recipe.add("microbiologicalInformation_microbiologicalOrganismWarningValue");
		recipe.add("ingredientStatement_");
	 	recipe.add("allergenStatement_");
	    recipe.add("transformationLink_transformedProduct");	
	    recipe.add("transformationLink_transformedweightUsedPerUC");
	    recipe.add("size_height");
	 	recipe.add("onPackFatPercentageInDryMatter_fatPercentageInDryMatterMeasurementPrecisionCode");
	 	recipe.add("physico_criteresObli/grasSurSec");
	 	recipe.add("allergenStatement");
	 	recipe.add("physico_typeAnalyse/extraitSec/maxi");
	 	recipe.add("physico_typeAnalyse/extraitSec/mini");
	 	recipe.add("physico_criteresObli/tauxMGTotMi");
	 	recipe.add("matierePremiere_matierePremiere");
	 	recipe.add("mustTradeItemBeChilled_mustTradeItemBeChilled");	 	
	 	recipe.add("nom_nom");	 	
	 	recipe.add("composition_isPrepared");
		recipe.add("infoConso_additionnelles/isRawMaterialIrradiated");
		recipe.add("fab_etapes");
		recipe.add("substitutedTradeItem_substitutedTradeItem/gtin");
		recipe.add("replacedTradeItem_replacedTradeItem/gtin");
		recipe.add("composition_nutriments/energieKcal/precision");	
		recipe.add("composition_nutriments/energieKcal/value");
		recipe.add("composition_nutriments/energieKcal/aqrPercentage");
		recipe.add("composition_nutriments/nutrimentsAutres/precision");
		
		recipe.add("composition_nutriments/energieKJ/precision");	
		recipe.add("composition_nutriments/energieKJ/value");	
		recipe.add("composition_nutriments/glucides/sucresReducteurs/precision");	
		recipe.add("composition_nutriments/glucides/sucresReducteurs/value");	
		recipe.add("composition_nutriments/lipides/acidesGrasSat/precision");	
		recipe.add("composition_nutriments/lipides/acidesGrasSat/value");	
		recipe.add("composition_nutriments/lipides/totaux/precision");	
		recipe.add("composition_nutriments/lipides/totaux/value");	
		recipe.add("composition_nutriments/proteines/precision");	
		recipe.add("composition_nutriments/proteines/value");	
		recipe.add("composition_nutriments/salt/precision");	
		recipe.add("composition_nutriments/salt/value");	
		recipe.add("composition_nutriments/glucides/totaux/precision");	
		recipe.add("composition_nutriments/glucides/totaux/value");	
		recipe.add("composition_nutriments/nutrimentsAutres/code");	
		recipe.add("composition_nutriments/nutrimentsAutres/value");
		recipe.add("composition_nutriments/nutrimentsAutres/aqrPercentage");
		recipe.add("composition_nutriments/nutrimentsAutres/precision");
		recipe.add("composition_nutriments/nutrimentsAutres/onPack");
		recipe.add("composition_nutriments/energieKcal/precision");	
		recipe.add("composition_nutriments/energieKcal/value");	
		recipe.add("composition_nutriments/nutrimentsAutres/precision");		
		recipe.add("composition_nutriments/energieKJ/rdiPrecision");
		recipe.add("composition_nutriments/energieKcal/rdiPrecision");
		recipe.add("composition_nutriments/glucides/sucresReducteurs/rdiPrecision");
		recipe.add("composition_nutriments/glucides/totaux/rdiPrecision");
		recipe.add("composition_nutriments/lipides/acidesGrasSat/rdiPrecision");
		recipe.add("composition_nutriments/lipides/totaux/rdiPrecision");
		recipe.add("composition_nutriments/proteines/rdiPrecision");
		recipe.add("composition_nutriments/salt/rdiPrecision");
		
		
		
		recipe.add("servingSize_energieKJ/precision");	
		recipe.add("servingSize_energieKJ/value");
		recipe.add("servingSize_energieKcal/precision");	
		recipe.add("servingSize_energieKcal/value");
		recipe.add("servingSize_glucides/sucresReducteurs/precision");	
		recipe.add("servingSize_glucides/sucresReducteurs/value");	
		recipe.add("servingSize_lipides/acidesGrasSat/precision");	
		recipe.add("servingSize_lipides/acidesGrasSat/value");	
		recipe.add("servingSize_lipides/totaux/precision");	
		recipe.add("servingSize_lipides/totaux/value");	
		recipe.add("servingSize_proteines/precision");	
		recipe.add("servingSize_proteines/value");	
		recipe.add("servingSize_salt/precision");	
		recipe.add("servingSize_salt/value");	
		recipe.add("servingSize_glucides/totaux/precision");	
		recipe.add("servingSize_glucides/totaux/value");
		recipe.add("servingSize_value");
		recipe.add("servingSize_unity");
		recipe.add("servingSize_other/code");
		recipe.add("servingSize_other/value");
		recipe.add("servingSize_other/precision");
		recipe.add("servingSize_other/aqrPercentage");
		recipe.add("servingSize_servingSizeDescriptions/text");		
		recipe.add("servingSize_energieKJ/aqrPercentage");
		recipe.add("servingSize_energieKcal/aqrPercentage");
		recipe.add("servingSize_glucides/sucresReducteurs/aqrPercentage");
		recipe.add("servingSize_glucides/totaux/aqrPercentage");
		recipe.add("servingSize_lipides/acidesGrasSat/aqrPercentage");
		recipe.add("servingSize_lipides/totaux/aqrPercentage");
		recipe.add("servingSize_proteines/aqrPercentage");
		recipe.add("servingSize_salt/aqrPercentage");
		recipe.add("servingSize_other/aqrPercentage");		
		recipe.add("servingSize_energieKJ/rdiPrecision");
		recipe.add("servingSize_energieKcal/rdiPrecision");
		recipe.add("servingSize_glucides/sucresReducteurs/rdiPrecision");
		recipe.add("servingSize_glucides/totaux/rdiPrecision");
		recipe.add("servingSize_lipides/acidesGrasSat/rdiPrecision");
		recipe.add("servingSize_lipides/totaux/rdiPrecision");
		recipe.add("servingSize_proteines/rdiPrecision");
		recipe.add("servingSize_salt/rdiPrecision");
		recipe.add("servingSize_other/rdiPrecision");
		
		
		recipe.add("rearingCountry_rearingCountry");
		recipe.add("ingredients_ingredientSequence");
		recipe.add("ingredients_countryOfOrigin");
		recipe.add("ingredients_animalSource");
		recipe.add("additives_additiveCode");
		recipe.add("additives_additiveComment");
		recipe.add("rennetTypeCode_rennetTypeCode");
		recipe.add("microbio_criteresHygiene/atp/method");
		recipe.add("microbio_criteresHygiene/atp/maximumValue");
		recipe.add("physico_code");
		recipe.add("physico_valeur");
		recipe.add("physico_maximumValue");
		recipe.add("physico_referenceValue");
		recipe.add("ingredients_ingredients");
		recipe.add("ingredients_countryOfOrigin");
		
		recipe.add("physico_critere/code");
		recipe.add("physico_critere/maximumValue");
		recipe.add("physico_critere/referenceValue");
		recipe.add("composition_nutriments/energieKJ/aqrPercentage");
		recipe.add("composition_nutriments/glucides/sucresReducteurs/aqrPercentage");
		recipe.add("composition_nutriments/glucides/totaux/aqrPercentage");
		recipe.add("composition_nutriments/lipides/acidesGrasSat/aqrPercentage");
		recipe.add("composition_nutriments/lipides/totaux/aqrPercentage");
		recipe.add("composition_nutriments/proteines/aqrPercentage");
		recipe.add("composition_nutriments/salt/aqrPercentage");
		recipe.add("preservationTechniqueTypeCode_preservationTechniqueTypeCode");
		recipe.add("ingredients_ingredientSequence");
		recipe.add("ingredients_countryOfOrigin");
		recipe.add("ingredients_animalSource");
		recipe.add("additives_additiveCode");
		recipe.add("additives_additiveComment");
		recipe.add("itemdisplayNameR_itemdisplayNameR");
		recipe.add("physico_typeAnalyse/tauxSel/maxi");
		recipe.add("physico_typeAnalyse/tauxSel/mini");
		recipe.add("physico/criteresAutres/critere_");
		recipe.add("IsPackagingMarkedWithIngredients_IsPackagingMarkedWithIngredients");
		recipe.add("processMicrobiologicalInformation_microbiological");
		recipe.add("microbiologicalInformation_microbiological");
		recipe.add("catchFishZone_catchFishZone");
		recipe.add("physico_typeAnalyse/tauxSel/mini");
		recipe.add("physico_typeAnalyse/tauxSel/maxi");
		recipe.add("physico_typeAnalyse/extraitSec/mini");
		recipe.add("physico_typeAnalyse/extraitSec/maxi");
		recipe.add("physico_criteresObli/grasSurSec");
		recipe.add("physico_criteresObli/tauxMGTotMi");		
		recipe.add("nutritionalClaimDetail_nutritionalClaimNonNutrientElement");
		recipe.add("nutritionalClaimDetail_nutritionalClaimNutrientElement");
		recipe.add("nutritionalClaimDetail_nutritionalClaimType");
		recipe.add("nutritionalClaimDetail_VariantnutritionalClaimNutrientElement");
		recipe.add("nutritionalClaimDetail_VariantnutritionalClaimType");
		recipe.add("nutritionalClaimDetail_nutritionalClaimType1");
		//recipe.add("numberOfServingsRangeDescription_numberOfServingsRangeDescription");
		//recipe.add("declaredUnity_declaredUnity");
		recipe.add("onPackFatPercentageInDryMatter_fatPercentageInDryMatter");		
		if (recipe.contains(attribute)) {
			value = true;
		}
		return value;
	}

	private static String getLookupValue(String attribute, String value) {
		String ivalue = value;
		if ((attribute.equals("Att_Adtt_mic_org_name"))  || (attribute.equals("Att_EU_Reg_mic_org_name")) || (attribute.equals("Att_EU_Reg_mic_crit_typ"))){
			if ((value.equals("ESCHERICHIA_COLI")) || (value.equals("ENTEROBACTERIA"))
					|| (value.equals("BACILLUS_CEREUS"))) {
				ivalue = "HYG";
			} else {
				ivalue = "SEC";
			}
		}
		if (attribute.equals("Att_Adtt_mic_org_meth")) {
			//if ((value.equals("ISO 16649-1")) || (value.equals("ISO 16649")) || (value.equals("ISO 16649-2"))) {
				// ivalue="ESCHERICHIA_COLI";
			//}
			if((value.equals("ESCHERICHIA_COLI"))){
				ivalue="ISO_16649";				
			}
			if((value.equals("POSITIVE_COAGULASE_STAPHYLOCOCCI"))){
				ivalue="EN/ISO 6888-1 or 2";				
			}
			if((value.equals("LISTERIA_MONOCYTOGENES"))){
				ivalue="EN/ISO 11290-1";				
			}
			if((value.equals("HISTAMINE"))){
				ivalue="EN ISO 19343";				
			}
			if((value.equals("ENTEROBACTERIA"))){
				ivalue="EN ISO 21528-2";				
			}
			if((value.equals("BACILLUS_CEREUS"))){
				ivalue="ISO_16649";				
			}
			if((value.equals("POSITIVE_COAGULASE_STAPHYLOCOCCI"))){
				ivalue="ISO_16649";				
			}

			if((value.equals("BACILLUS_CEREUS"))){
				ivalue="EN/ISO 7932";				
			}

			if((value.equals("CRONOBACTER_SPP"))){
				ivalue="EN/ISO 22964";				
			}
			if((value.equals("STAPHYLOCOCCUS_ENTEROTOXIN"))){
				ivalue="EN/ISO 19020";				
			}
			if((value.equals("SALMONELLA"))){
				ivalue="EN/ISO 6579";				
			}
			
		}
		if (attribute.equals("Att_Ref_tra_typ_cod")) {
			if ((value.equals("substitutedTradeItem"))) {
				ivalue = "SUBSTITUTED_BY";
			}
			if ((value.equals("replacedTradeItem"))) {
				ivalue = "REPLACED_BY";
			}

		}
		if (attribute.equals("Att_Award_Prz_Jur")) {
			if ((value.equals("AP01")) || (value.equals("AP02"))|| (value.equals("AP03"))) {
				ivalue = "Concours G n ral Agricole";
			}
			if ((value.equals("AP04"))) {
				ivalue = "Monadia";
			}
			if ((value.equals("AP05"))) {
				ivalue = "Management Europe Meeting";
			}
			if ((value.equals("AP06"))) {
				ivalue = "Trial panel";
			}
			if ((value.equals("AP07"))) {
				ivalue = "World Championship cheese contest USA";
			}
			if ((value.equals("AP08"))) {
				ivalue = "Armonia";
			}



		}

		return ivalue;
	}

	private static String[] splitInputData(String input) {

		String[] arrOfStr = input.split("#", 5);

		return arrOfStr;

	}
	private static String splitInputDataSlash(String input) {
		String x=input;
		if(input.contains("energieKcal"))
		{
			 x="ENERC";
		}
		if(input.contains("energieKJ"))
		{
			 x="ENER-";
		}

		if(input.contains("glucides/sucresReducteurs"))
		{
			 x="SUGAR-";
		}
		if(input.contains("glucides/totaux"))
		{
			 x="CHOAVL";
		}
		if(input.contains("lipides/acidesGrasSat"))
		{
			 x="FASAT";
		}
		if(input.contains("lipides/totaux"))
		{
			 x="FAT";
		}
		if(input.contains("proteines") || input.contains("PROTEIN"))
		{
			 x="PRO-";
		}
		if(input.contains("salt"))
		{
			 x="SALTEQ";
		}

		if(input.contains("other") ||input.contains("nutrimentsAutres") || input.contains("CALCIUM") ){
			x="CA";
		}
		return x;
	}
	private static String nutriant(String input) {
		String x=input;
		if(input.contains("LACTOSE"))
		{
			 x="LACS";
		}		
		if(input.contains("POTASSIUM"))
		{
			 x="K";
		}
		if(input.contains("VITAMIN_D"))
		{
			 x="VITD-";
		}
		if(input.contains("BIOTIN"))
		{
			 x="BIOT";
		}
		if(input.contains("CALCIUM"))
		{
			 x="CA";
		}
		if(input.contains("CALORIE"))
		{
			 x="ENERC";
		}

		if(input.contains("CHLORIDE")){
			x="CLD";
		}
		if(input.contains("CHOLESTEROL")){
			x="CHOLC";
		}
		if(input.contains("CHROMIUM")){
			x="CR";
		}
		if(input.contains("COPPER")){
			x="CU";
		}
		if(input.contains("ENERGY")){
			x="ENER-";
		}
		if(input.contains("FAT")){
			x="FAT";
		}
		if(input.contains("FIBRE")){
			x="FIBTG";
		}
		if(input.contains("FLUORIDE")){
			x="FD";
		}
		if(input.contains("FOLIC_ACID")){
			x="FOLDFE";
		}
		if(input.contains("IODINE")){
			x="ID";
		}
		if(input.contains("IRON")){
			x="FE";
		}
		if(input.contains("MAGNESIUM")){
			x="MG";
		}
		if(input.contains("GLUCIDES")){
			x="CHOAVL";
		}
		if(input.contains("MANGANESE")){
			x="MN";
		}
		if(input.contains("MOLYBDENUM")){
			x="MO";
		}
		if(input.contains("MONO_UNSATURATED_FAT")){
			x="FAMSCIS";
		}
		if(input.contains("NIACIN")){
			x="NIA";
		}
		if(input.contains("OMEGA_3_FATTY_ACIDS")){
			x="AGO3";
		}
		if(input.contains("PANTOTHENIC_ACID")){
			x="PANTAC";
		}
		if(input.contains("PHOSPHORUS")){
			x="P";
		}
		if(input.contains("POLYUNSATURATED_FAT")){
			x="FAPUCIS";
		}
		if(input.contains("PROTEIN")){
			x="PRO-";
		}
		if(input.contains("RIBOFLAVIN")){
			x="RIBF";
		}
		if(input.contains("SATURATED_FAT")){
			x="FASAT";
		}
		if(input.contains("SELENIUM")){
			x="SE";
		}
		if(input.contains("SODIUM_SALT")){
			x="SALTEQ";
		}
		if(input.contains("SUGARS")){
			x="SUGAR-";
		}
		if(input.contains("THIAMIN")){
			x="THIA";
		}
		if(input.contains("TRANS_FAT")){
			x="F16D1T";
		}
		if(input.contains("VITAMIN_A")){
			x="VITA-";
		}
		if(input.contains("VITAMIN_B12")){
			x="VITB12";
		}
		if(input.contains("VITAMIN_B6")){
			x="VITB6-";
		}
		if(input.contains("VITAMIN_C")){
			x="VITC-";
		}
		if(input.contains("VITAMIN_E")){
			x="VITE-";
		}
		if(input.contains("VITAMIN_K")){
			x="VITK-";
		}
		if(input.contains("ZINC")){
			x="ZN";
		}
		return x;
	}
}

