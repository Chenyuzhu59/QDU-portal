# Parallel-Meta Suite Users’ Manual

![Version](https://img.shields.io/badge/Version-3.7Alpha-brightgreen)
![Release date](https://img.shields.io/badge/Release%20date-Nov.%2012%2C%202021-brightgreen)

## Contents
- [Introduction](#introduction)
- [Installation Guide](#installation-guide)
- [Typical Usages](#typical-usages)
- [Tools in toolkit](#tools-in-toolkit)
- [Results interpretation](#results-interpretation)
- [Contact Us](#contact)

# Introduction

Parallel-Meta Suite (PMS), a visualized and interactive software package for microbiome analysis. It not only implements a comprehensive workflow that covers from sequence processing to chart plotting by state-of-art algorithms, but also provides an easy-to-use graphical interface for configuration and results interpretation, which is easy to startup for all-level users. More importantly, the entire procedure of PMS has been optimized by parallel computing, enabling the rapid screening of thousands of samples. Both metagenomic shotgun sequences and 16S/18S/ITS rRNA amplicon sequences are accepted.

We strongly recommend that read this manually carefully before use Parallel-Meta Suite.

# Installation guide

## Conda installation (recommended)
Miniconda provides the conda environment and package manager, and is the recommended way to install PMS. Follow the [Miniconda instructions](https://conda.io/projects/conda/en/latest/user-guide/install/index.html) for downloading and installing Miniconda.

a. Add channels
	
	conda config --add channels default
	conda config --add channels bioconda
	conda config --add channels conda-forge

b. Install
	
	conda install parallel-meta-suite

c. Download databases and other files
	
	PM-install

## Source code package installation
#### Automatic installation
PMS also provides a fully automatic installer for easy installation.

a. Extract the package:

	tar –xzvf parallel-meta-suite.tar.gz

b. Install
	
	cd parallel-meta
	source install.sh


##### Tips for the Automatic installation

1. Please **“cd parallel-meta”** before run the automatic installer.
2. The automatic installer only configures the environment variables to the default configuration files of “\~/.bashrc” or “\~/.bash_profile”. If you want to configure the environment variables to other configuration file please use the manual installation.
3. If the automatic installer failed, PMS can still be installed manually by the following steps.

#### Manual installation

If the automatic installer failed, PMS can still be installed manually.

a. Extract the package:

	tar –xzvf parallel-meta-suite.tar.gz

b. Configure the environment variables (default environment variable configuration file is located at “\~/.bashrc” or “\~/.bash_profile”)

	export ParallelMETA=Path to Parallel-Meta
	export PATH="$PATH:$ParallelMETA/bin"
	export PATH="$PATH:$ParallelMETA/Rscript"
	source ~/.bashrc

c. Install R packages
	
	Rscript $ParallelMETA/Rscript/config.R

d. Compile the source code:

	cd parallel-meta
	make


# Typical usages
![usage.png](https://i.loli.net/2021/11/12/AX4E3Hjzeg5crCp.png)
## GUI-based configuration and run in a local computer

#### Local GUI Configure

Local GUI-based usage is applicable with operating systems of Linux (GUI desktop installed), MAC OS, or Windows 10 (Subsystem for Linux (WSL) installed)

a. Open the **"index.html"** page in the Homepage folder of the software

b. Adjust parameters or keep the default options (according to actual requirements)

c. Click the **“Generate”** button at the bottom of the page to get a command

d. Click the **“Copy”** button to copy it into clipboard

#### Local Run
a. Paste this single-line command in the local terminal, and it will perfectly run Parallel-Meta Suite pipeline without other operations.

#### Local GUI View
a. Enter output directory
b. Open the visualized result viewer named as **"index.html"**

Notes:
1. All original results (e.g. relative abundance table, distance matrix, etc.) are kept for further in-depth data mining or meta-analysis. 
2. In addition, the analysis summary, work log and detailed step-by-step workflow script are also provided in the result folder. 

## Local GUI-based configuration and remote run in a server

#### Local GUI Configure (for servers)

Usually, servers need remote login (e.g. via SSH) and only provide command-based terminal.  in the local computer to generate the command.

a. Download GUI configuration guild named **"index.html"** in the Homepage folder of the software

b. Generate the command in the local computer as well as [local GUI configure](####local-gui-configure)

#### Remote Run
a. Paste and run the command in the terminal of remote server.

#### Local GUI View (for servers)
The results can also be transfer to the local computer for browsing as well as [local GUI view](####local-gui-view).

## Command-based configuration and run
PMS also support command-based operations for non-GUI conditions, or experienced users. It is available via terminal either locally or remotely. 

# Tools in toolkit
Tools can be directly used as Linux command line with parameters. To see all available parameters, please run the command with parameter ‘-h’, e.g.

	PM-pipeline –h
	

### C++ based implementations
| Command name  |  Purpose |
| :------------ | :------------ |
| PM-pipeline  |  Automatic analysis workflow. This tool integrates all other tools in this Table. |
|PM-format-seq|	Sequence format check and reformation for PMS|
|PM-extract-rna|	Extract amplicon fragments from shotgun sequences|
|PM-parallel-meta|	Microbiome sample profiling|
|PM-select-taxa|	Relative abundance table on selected taxonomy levels|
|PM-rand-rare|	Samples rarefaction|
|PM-predict-func|	Functional prediction|
|PM-predict-func-nsti|	Compute the NSTI for predicted functions|
|PM-predict-func-contribute|	Compute the contribution of taxon for predicted functions|
|PM-select-func|	Relative abundance table on selected pathway levels|
|PM-plot-taxa|	Taxonomy profile visualization by Krona|
|PM-rare-curv|	Make the rarefaction curve|
|PM-comp-taxa|	Compute the Meta-Storms distance among samples using taxonomy profiles|
|PM-comp-func|	Compute the Hierarchical Meta-Storms functional distance among samples using function profiles|
|PM-comp-corr|	Compute the correlation among community members, and construct the co-occurrence network|
|PM-split-seq|	Split sequences by sample from merged sequences (e.g. QIIME format)|
|PM-update-taxa|	Update the taxonomy annotation to the latest version|

### R based implementations
|R-script name|	Purpose|
| :------------ | :------------ |
|PM_Distribution.R|	Plot relative abundances into bar-chat|
|PM_Adiversity.R|	Alpha diversity indexes calculation and plotting|
|PM_Bdiversity.R|	Beta diversity PERMANOVA and ANOSIM tests and plotting using distance matrix|
|PM_Hcluster.R|	Hierarchical clustering by distance matrix|
|PM_Heatmap.R|	Heatmap of distance matrix|
|PM_rarefaction.R|	Rarefaction curve plotting|
|PM_Pca.R|	PCA and plotting|
|PM_Pcoa.R|	PCoA and plotting|
|PM_Marker_Test.R|	Candidate biomarker selection by rand-sum test (for discrete meta-data variables)|
|PM_Marker_Corr.R|	Candidate biomarker selection by regression (for numberic meta-data variables)|
|PM_Marker_RFscore.R|	Biomarker ranking by Random Forest importance|
|PM_Network.R|	Co-occurence network plotting using correlation among community members|

# Results interpretation

After using PM-pipeline, you might get the following folders/files in the output directory. In each directory, files/tables/figures are named with prefix “taxa” are taxonomy results, as well as “func” are metabolic functional results. PMS also provides an index page("index.html" in output directory) for results browsing.

## index.html (web page)

This is the index page to browse for results browsing. Users can open it by a webpage browser and view the detailed results by hyperlinks. Please notice that

a. the “index.html” only works in the output directory;

b. JavaScript, SVG, HTML5 and PDF should be supported by the browser;

c. Links may not available with customized parameters. See "More results" for all available results.

## Sample_Views (dir)

This directory contains the visualized sample view (taxonomy.html, JavaScript, SVG and HTML5 should be supported) in interactive pie charts across multiple samples.

## Abundance_Tables (dir)

This directory contains the relative abundance tables (*.Abd), absolute sequence count tables (*.Count), and bar charts (*Abd.pdf) of multiple samples on different taxonomical and functional levels.

## Distance_Matrix (dir)

This directory contains the pair-wised distance matrix (*.dist) of all input samples and unsupervised clustering results (*.dist.clusters.pdf and *.dist.heatmap.pdf) based on OTUs and KO profiles of multiple samples. Distances are computed based on Metastorms algorithm [Su, et al., Bioinformatics, 2012] and Hierarchical Metastorms algorithm [Zhang, et al., Bioinformatics Advances, 2021].

## Clustering (dir)

This directory contains the supervised clustering results based on PCA (*.pca.pdf) and PCoA (*.pcoa.pdf).

## Alpha_Diversity (dir)

This directory contains the multivariate statistical analysis results (*.Alpha_diversity_Boxplot.pdf* and  *.Alpha_diversity_Index.txt*) and rarefaction curve of alpha diversity. P-values are estimated by Wilcoxon rank-sum tests.

## Beta_Diversity (dir)

This directory contains the multivariate statistical analysis results (*.Beta_diversity_Boxplot.pdf *and *.taxa.dist.Beta_diversity_Values.txt*) of beta diversity. P-values are estimated by Anosim, Adonis/Permanova tests.

## Markers (dir)

This directory contains the biomarker organisms (*.sig.boxplot.pdf and *.sig.meanTests.xls) and their Random Forest scores (*.RFimportance.pdf and *. RFimportance.txt) among different groups.

## Network (dir)

This directory contains the microbial interaction network (*.network.pdf) based on different taxonomical and functional levels.

## Single_Sample (dir)

In this directory, each sub folder is the detailed information of an individual sample named by the sample ID. In the sub directories there may be

a. classification.txt (plain-text file): The OTUs and taxonomy information of this sample.

b. taxonomy.html (HTML webpage): The visualized sample view in interactive pie chart of this sample.

c. Analysis_Report.txt (plain-text file): The analysis report including parameters configuration and analysis information statistics.

d. meta.rna (fasta sequences): The extracted 16S/18S rRNA fragment, if the input is metageomic shotgun sequences.


## Single_Sample.List (dir)

This directory contains the profiling results path list (named as taxa.list, see Single sample profile list) of all samples. Each list has 2 columns: the first column is the samples’ ID and the second column is the path of the profiling result.

## Logs (plain-text file)

a. Analysis_Report.txt: The analysis report including parameters configuration and analysis information statistics.

b. scripts.sh: The detailed scripts of each analysis step.

c. error.log: The warning and error messages.

# Contact

Any problem please contact Parallel-Meta Suite development team

	Chen Yuzhu	E-mail: chenyuzhu_study@163.com
	Li Jian	E-mail: rendcloud@163.com

