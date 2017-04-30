/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/


package com.example.Muyao.myapplication.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.*;


public class MyServlet extends HttpServlet {

    String ourStuff ="CUL3: 32,405 European ancestry cases, 42,221 European ancestry controls, 1,235 European ancestry cases and 1,235 European ancestry controls from 1235 parent-offspring trios, 1,836 East Asian ancestry cases, 3,383 East Asian ancestry controls\n" +
            "MAP3K7: Response to inhaled corticosteroid treatment in asthma (percentage change of FEV1)\n" +
            "USP53: Corneal astigmatism\n" +
            "INA: Migraine\n" +
            "KIF5B: Bipolar disorder with mood-incongruent psychosis\n" +
            "VPS45: Uric acid levels\n" +
            "KIF5C: Autism spectrum disorder, attention deficit-hyperactivity disorder, bipolar disorder, major depressive disorder, and schizophrenia (combined)\n" +
            "SCYL1: Bone mineral density\n" +
            "SIPA1L1: Response to alcohol consumption (flushing response)\n" +
            "ARHGEF28: D-dimer levels\n" +
            "CNTRL: Immune reponse to smallpox (secreted IFN-alpha)\n" +
            "PIBF1: Blood pressure measurement (low sodium intervention)\n" +
            "NDC80: PR interval in Tripanosoma cruzi seropositivity\n" +
            "PKP2: Obesity-related traits\n" +
            "BACH1: Visceral fat\n" +
            "LZTS2: Ejection fraction in Tripanosoma cruzi seropositivity\n" +
            "BACH2: Celiac disease\n" +
            "GRIP1: Suicidal ideation in depression or bipolar disorder\n" +
            "SMAP2: Ejection fraction in Tripanosoma cruzi seropositivity\n" +
            "EVI5: Cholesterol, total\n" +
            "USP15: Pyoderma gangrenosum in inflammatory bowel disease\n" +
            "PIK3C2A: Response to abacavir-containing treatment in HIV-1 infection (virologic failure)\n" +
            "ARHGEF18: Sexual dysfunction (SSRI/SNRI-related)\n" +
            "TP53: Basal cell carcinoma\n" +
            "BRWD1: Up to 182,413 European ancestry females\n" +
            "PHLDB1: Cholesterol, total\n" +
            "ABI2: Butyrylcholinesterase levels\n" +
            "SORBS1: 683 European ancestry cases, 779 European ancestry controls\n" +
            "SORBS2: Visceral adipose tissue/subcutaneous adipose tissue ratio\n" +
            "EXOC4: Alzheimer's disease (cognitive decline)\n" +
            "EXOC3: 1,006 European ancestry male child cases, 2,390 European ancestry male child controls, 3,096 European ancestry female child controls\n" +
            "IGF1: 36,227 East Asian ancestry individuals\n" +
            "IGF2: Femoral neck bone geometry and menarche (age at onset)\n" +
            "MID1: Cognitive performance\n" +
            "GSK3B: HDL cholesterol\n" +
            "AP4B1: Obesity-related traits\n" +
            "PHLDB2: up to 1,090 Southern European ancestry Crohn's disease cases\n" +
            "ENAH: Obesity-related traits\n" +
            "KIAA1598: Heart failure\n" +
            "HBS1L: Cholesterol, total\n" +
            "CSNK2A2: Telomere length\n" +
            "BAIAP2: Memory performance\n" +
            "TACC3: Bladder cancer\n" +
            "BICD1: Obesity-related traits\n" +
            "OTUD3: Ulcerative colitis\n" +
            "C2CD4C: Obesity-related traits\n" +
            "ARPC2: White blood cell count\n" +
            "FGD6: Adverse response to chemotherapy (neutropenia/leucopenia) (paclitaxel + carboplatin)\n" +
            "PALM2-AKAP2: Height\n" +
            "ABL2: IgE grass sensitization\n" +
            "E2F5: Plasma amyloid beta peptide concentrations (ABx-40)\n" +
            "SPRY4: Testicular germ cell tumor\n" +
            "SH2D4A: Smooth-surface caries\n" +
            "CCHCR1: Nevirapine-induced rash\n" +
            "CASP8: Chronic lymphocytic leukemia\n" +
            "CNTLN: Lewy body disease\n" +
            "IRF2BP2: Cholesterol, total\n" +
            "KIF1B: Mean platelet volume\n" +
            "STXBP5: Plasma plasminogen activator levels\n" +
            "SNX18: Immune response to smallpox vaccine (IL-6)\n" +
            "HAUS2: up to 4,176 Mexican American individuals\n" +
            "HAUS1: Migraine - clinic-based\n" +
            "TRDMT1: Telomere length\n" +
            "NFATC3: 32,405 European ancestry cases, 42,221 European ancestry controls, 1,235 European ancestry cases and 1,235 European ancestry controls from 1235 parent-offspring trios, 1,836 East Asian ancestry cases, 3,383 East Asian ancestry controls\n" +
            "CBL: Platelet counts\n" +
            "SCRN1: Obesity-related traits\n" +
            "C17orf53: Bone mineral density\n" +
            "RNF114: Systemic lupus erythematosus\n" +
            "FAM110B: 979 European ancestry individuals\n" +
            "LUZP1: 253,288 European ancestry individuals\n" +
            "KIFAP3: Amyotrophic lateral sclerosis\n" +
            "TANC1: Response to radiotherapy in prostate cancer (toxicity)\n" +
            "TANK: Response to anti-retroviral therapy (ddI/d4T) in HIV-1 infection (Grade 3 peripheral neuropathy)\n" +
            "LCA5: Presence of antiphospholipid antibodies\n" +
            "DLD: Ulcerative colitis\n" +
            "EEFSEC: Up to 182,413 European ancestry females\n" +
            "MYO9B: Glycated hemoglobin levels\n" +
            "ALMS1: Blood metabolite levels\n" +
            "CEP85L: Up to 70,389 European ancestry individuals, up to 672 Orcadian individuals\n" +
            "RNF2: Obesity-related traits\n" +
            "FERMT2: Alzheimer's disease (late onset)\n" +
            "VPS37A: Tonometry\n" +
            "TTLL5: Large artery stroke\n" +
            "VPS37C: Rheumatoid arthritis\n" +
            "MCCC1: 13,708 European ancestry cases, 95,282 European ancestry controls\n" +
            "C12orf23: Bone mineral density\n" +
            "RAD50: Asthma (childhood onset)\n" +
            "MTAP: Melanoma\n" +
            "IFFO2: 657 European ancestry cases, 9,296 European ancestry controls\n" +
            "PPP6R3: Bone mineral density (paediatric, skull)\n" +
            "APBA2: Response to mTOR inhibitor (rapamycin)\n" +
            "EIF4ENIF1: Age-related hearing impairment\n" +
            "CSNK1A1: Esophageal cancer\n" +
            "BCKDHB: 253,288 European ancestry individuals\n" +
            "TEX9: 161 European ancestry cases, 1,196 European ancestry clozapine-treated and untreated controls\n" +
            "SVIL: Neurofibrillary tangles\n" +
            "BCDIN3D: Up to 182,413 European ancestry females\n" +
            "CEP72: Obesity-related traits\n" +
            "TOP1: Cholesterol, total\n" +
            "PRKACB: Breast cancer (male)\n" +
            "CEP85: Obesity-related traits\n" +
            "CARS: Calcium levels\n" +
            "ERC1: Bone mineral density\n" +
            "GPN1: Periodontitis (CDC/AAP)\n" +
            "CEP95: PR interval in Tripanosoma cruzi seropositivity\n" +
            "ITGB1: Depression (quantitative trait)\n" +
            "LOH12CR1: Inflammatory bowel disease\n" +
            "BRCA2: Lung cancer\n" +
            "NEDD1: Autism\n" +
            "CADPS2: Up to 182,413 European ancestry females\n" +
            "PLEKHA7: Glaucoma (primary angle closure)\n" +
            "SLAIN2: Crohn's disease\n" +
            "ARHGAP10: Immune reponse to smallpox (secreted IL-12p40)\n" +
            "ARHGAP12: C-reactive protein levels\n" +
            "FAM83B: Up to 182,413 European ancestry females\n" +
            "TMEM57: LDL cholesterol\n" +
            "KLHL22: Retinopathy in non-diabetics\n" +
            "GAREM: 6,085 Korean ancestry individuals\n" +
            "KIAA0232: Platelet counts\n" +
            "PALLD: Response to iloperidone treatment (QT prolongation)\n" +
            "KLHL13: Iron status biomarkers\n" +
            "SMARCAD1: Up to 182,413 European ancestry females\n" +
            "CCDC57: Blood metabolite levels\n" +
            "C6orf132: Amyotrophic lateral sclerosis (sporadic)\n" +
            "TNRC6C: Thiazide-induced adverse metabolic effects in hypertensive patients\n" +
            "TNRC6B: Prostate cancer\n" +
            "TNRC6A: Type 2 diabetes (dietary heme iron intake interaction)\n" +
            "ZC3H12C: Exercise treadmill test traits\n" +
            "CEP63: Height\n" +
            "KNSTRN: 1,778 European ancestry individuals, 1,276 individuals\n" +
            "CCDC77: Obesity (early onset extreme)\n" +
            "ATXN3: Coronary artery calcification\n" +
            "NME1-NME2: Height\n" +
            "EVI5L: Up to 182,413 European ancestry females\n" +
            "CROCC: Allergic rhinitis\n" +
            "RANGAP1: Self-reported allergy\n" +
            "WDR73: Periodontitis (CDC/AAP)\n" +
            "MAP2K6: Response to anti-retroviral therapy (ddI/d4T) in HIV-1 infection (Grade 1 peripheral neuropathy)\n" +
            "MAGI3: Thyroid peroxidase antibody levels\n" +
            "TNIK: LDL cholesterol\n" +
            "MAGI1: up to 1,090 Southern European ancestry Crohn's disease cases\n" +
            "PCM1: Up to 737 European ancestry individuals, Up to 545 Black individuals, Up to 314 Hispanic individuals\n" +
            "NME7: Venous thromboembolism\n" +
            "FCHSD2: Crohn's disease\n" +
            "WDR60: Height\n" +
            "CCDC102A: Cataracts in type 2 diabetes\n" +
            "PVRL2: Verbal declarative memory\n" +
            "FXR2: IgM levels\n" +
            "FXR1: Schizophrenia\n" +
            "NAP1L1: Obesity-related traits\n" +
            "TTC27: Hippocampal atrophy\n" +
            "STOX2: Thiazide-induced adverse metabolic effects in hypertensive patients\n" +
            "CEP170B: IgG glycosylation\n" +
            "WDR34: Axial length\n" +
            "SIK3: 1,022 European ancestry individuals, 280 Carlantino individuals, 1,097 Friuli Venezia Giulia individuals, 804 Korkula individuals, 497 Split individuals, 421 Cilento individuals, 470 Talana individuals, 348 Silk Road individuals\n" +
            "ARHGEF7: Homeostasis model assessment of insulin resistance (interaction)\n" +
            "ACO1: IgG glycosylation\n" +
            "MAST4: Epilepsy (generalized)\n" +
            "KIAA1217: Common traits (Other)\n" +
            "HK2: Femoral neck bone geometry\n" +
            "PLEKHG1: Systolic blood pressure in sickle cell anemia\n" +
            "TBC1D5: 32,405 European ancestry cases, 42,221 European ancestry controls, 1,235 European ancestry cases and 1,235 European ancestry controls from 1235 parent-offspring trios, 1,836 East Asian ancestry cases, 3,383 East Asian ancestry controls\n" +
            "CSDE1: Autism\n" +
            "TBC1D4: Heart failure\n" +
            "HSD17B4: Pyoderma gangrenosum in inflammatory bowel disease\n" +
            "DNM3: 36,227 East Asian ancestry individuals\n" +
            "PDZRN3: QT interval\n" +
            "DNM1: Attention deficit hyperactivity disorder\n" +
            "CAPZA1: 253,288 European ancestry individuals\n" +
            "RPS6KB1: Obesity-related traits\n" +
            "AAK1: 4,501 European ancestry individuals, 351 African American individuals\n" +
            "ACTN1: Periodontitis (Mean PAL)\n" +
            "ARPC1A: Dehydroepiandrosterone sulphate levels\n" +
            "MAVS: Asthma\n" +
            "ECE1: 253,288 European ancestry individuals\n" +
            "NIN: Lupus nephritis in systemic lupus erythematosus\n" +
            "TSC1: Psoriasis\n" +
            "DPH5: QT interval (interaction)\n" +
            "LCP1: Non-alcoholic fatty liver disease histology (other)\n" +
            "ALDH1A2: Osteoarthritis (hand, severe)\n" +
            "REL: Hodgkin's lymphoma\n" +
            "SCLT1: Chronic obstructive pulmonary disease (moderate to severe)\n" +
            "DPYSL5: Obesity-related traits\n" +
            "DBI: Obesity-related traits\n" +
            "ANXA2: 8,284 African American cases, 15,543 African American controls\n" +
            "PDLIM5: Lewy body disease\n" +
            "SNCA: 13,708 European ancestry cases, 95,282 European ancestry controls\n" +
            "CD2AP: Amyotrophic lateral sclerosis (sporadic)\n" +
            "PRIM1: Menopause (age at onset)\n" +
            "SHB: Metabolite levels (Dihydroxy docosatrienoic acid)\n" +
            "GAB1: Asthma\n" +
            "CEP152: Insomnia\n" +
            "NAV1: Celiac disease\n" +
            "GIGYF2: 32,405 European ancestry cases, 42,221 European ancestry controls, 1,235 European ancestry cases and 1,235 European ancestry controls from 1235 parent-offspring trios, 1,836 East Asian ancestry cases, 3,383 East Asian ancestry controls\n" +
            "TDRD3: Breast size\n" +
            "CDV3: Bulimia nervosa\n" +
            "AKAP13: Corneal structure\n" +
            "IGF2BP1: Primary tooth development (time to first tooth eruption)\n" +
            "ASAP1: Multiple sclerosis\n" +
            "AKAP10: Platelet counts\n" +
            "IGF2BP3: 253,288 European ancestry individuals\n" +
            "CAPZB: Thyroid hormone levels\n" +
            "PIN1: 47,180 European ancestry individuals\n" +
            "CEP170: 106,736 European ancestry individuals\n" +
            "TBKBP1: Ankylosing spondylitis\n" +
            "PTPN14: 36,227 East Asian ancestry individuals\n" +
            "TRIO: 2,383 European ancestry adolescents and 1,643 European ancestry adults from 1,613 families\n" +
            "HOMER1: 4,162 European ancestry individuals\n" +
            "EPS8: Protein quantitative trait loci\n" +
            "TMOD1: Obesity-related traits\n" +
            "FAM91A1: Pancreatic cancer\n" +
            "BCAR1: 1,582 European ancestry cases, 5,203 European ancestry controls\n" +
            "WASF2: Estradiol plasma levels (breast cancer)\n" +
            "NFKB1: Ulcerative colitis\n" +
            "BAG3: Dilated cardiomyopathy\n" +
            "DYNC1H1: Menopause (age at onset)\n" +
            "DYNC1I1: Dementia and core Alzheimer's disease neuropathologic changes\n" +
            "HERC2: Eye color\n" +
            "ZCCHC14: Major depressive disorder\n" +
            "IFT74: Amyotrophic lateral sclerosis (sporadic)\n" +
            "CEP120: 36,227 East Asian ancestry individuals\n" +
            "SHROOM3: Glomerular filtration rate\n" +
            "KIAA0196: 4,377 European ancestry ALS cases, 435 European ancestry FTD cases, 14,431 European ancestry controls\n" +
            "POLR2B: 36,227 East Asian ancestry individuals\n" +
            "STAU1: 253,288 European ancestry individuals\n" +
            "CYLD: Leprosy\n" +
            "CIT: 1,102 European ancestry cases, 405 European ancestry controls\n" +
            "TJP2: 3,269 European ancestry individuals\n" +
            "MAEA: Type 2 diabetes\n" +
            "CHMP4C: Ovarian cancer\n" +
            "PARD3B: Attention deficit hyperactivity disorder (combined symptoms)\n" +
            "ITPK1: Thyroid hormone levels\n" +
            "POC5: Body mass index\n" +
            "PCCB: Height\n" +
            "PCCA: Refractive error\n" +
            "CGNL1: Bipolar disorder (age of onset and psychotic symptoms)\n" +
            "COPZ1: Platelet counts\n" +
            "AXIN1: Bone mineral density\n" +
            "CPNE8: Heart rate\n" +
            "LPP: up to 4,176 Mexican American individuals\n" +
            "CKAP5: Bone mineral density (hip)\n" +
            "RPGRIP1L: Vitiligo (non-segmental)\n" +
            "TOMM40: Alzheimer's disease\n" +
            "ATE1: Breast cancer\n" +
            "GPATCH1: Bone properties (heel)\n" +
            "KIAA0355: Functional MRI\n" +
            "EIF4E2: 253,288 European ancestry individuals\n" +
            "CAMSAP2: Epilepsy\n" +
            "ARHGAP1: Bone mineral density\n" +
            "TM9SF2: Obesity-related traits\n" +
            "HSP90AA1: Behavioural disinhibition (generation interaction)\n" +
            "CCDC88C: Breast cancer\n" +
            "C5orf42: Telomere length\n" +
            "CALD1: Response to anti-retroviral therapy (ddI/d4T) in HIV-1 infection (Grade 1 peripheral neuropathy)\n" +
            "DCTD: Wegener's granulomatosis\n" +
            "RUFY1: Eating disorders\n";


    HashMap<String,String> database = new HashMap<>();

    private boolean isInDB(String key)
    {
       return(database.containsKey(key));
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        if (name == null) {
            out.println("Please enter a name");
        }

        if(name.toString().length()>1){
            String[] array = ourStuff.split("\n");
            for(int ctr = 0;ctr<array.length;ctr++) {
                int k = 0;
                while(k<array[ctr].length()&&array[ctr].charAt(k)!=':')
                {
                    k++;
                }
                if(k<array[ctr].length())
                    database.put(array[ctr].substring(0, k), array[ctr].substring(k+1));
            }
            if (database.containsKey(name))
                out.println(name +" is the gene responsible for "+ database.get(name));
            else if (name.toString().contains(":")){
                ourStuff=ourStuff+name+"\n";
                out.println("New entry added: "+name);
            }
            else if(name.toString().contains("SHOWALL"))
                out.println();
            else
                out.println("Gene not found!");

        }
    }
}