# Utilisation de la nouvelle adresse Harbor
FROM newrepo.star.com.tn/library/jboss6:star

LABEL maintainer="jasser boukraya"

# Copie de la configuration (assurez-vous que le fichier est présent dans votre SVN)
COPY standalone.xml /opt/jboss-eap-6.1/standalone/configuration/

# Copie de l'application générée par le stage Maven précédent
# Le chemin correspond au module EAR de votre projet
COPY Ba-ear/target/*.ear /opt/jboss-eap-6.1/standalone/deployments/

# Copie du dossier des PDF (assurez-vous qu'il est présent dans le checkout SVN)
COPY pdfbancass /opt/pdfbancass

# Ports standards JBoss
EXPOSE 8080 9990 4447 9999

# Lancement du serveur
CMD ["/opt/jboss-eap-6.1/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]