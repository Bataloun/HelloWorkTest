<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

    <!-- Template principal : transformation du document -->
    <xsl:template match="/Root">
        <Result>
            <!-- Itération sur chaque élément "Offer" -->
            <xsl:for-each select="Offer">
                <TransformedOffer>
                    <UpperCase>
                        <!-- Convertir la description en majuscules -->
                        <xsl:value-of select="translate(Description,
                        'abcdefghijklmnopqrstuvwxyzéèêëàâäùûüîïç',
                        'ABCDEFGHIJKLMNOPQRSTUVWXYZÉÈÊËÀÂÄÙÛÜÎÏÇ')" />

                    </UpperCase>
                    <Reference>
                        <xsl:value-of select="Reference" />
                    </Reference>
                    <UTCDate>
                        <!-- Transformer la date française en UTC (simple formatage pour cet exemple) -->
                        <xsl:value-of select="concat(
                            substring(FrenchDate, 7, 4), '-',
                            substring(FrenchDate, 4, 2), '-',
                            substring(FrenchDate, 1, 2), 'T',
                            substring(FrenchDate, 12, 8), 'Z'
                        )" />
                    </UTCDate>
                </TransformedOffer>
            </xsl:for-each>
        </Result>
    </xsl:template>

</xsl:stylesheet>
