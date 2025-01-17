<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:java="java:testHelloWork.util.XSLTFunctions"
                version="1.0">

    <xsl:template match="/Root">
        <Result>
            <xsl:for-each select="Offer">
                <TransformedOffer>
                    <UpperCase>
                        <xsl:value-of select="java:toUpperCase(Description)" />
                    </UpperCase>
                    <Reference>
                        <xsl:value-of select="Reference" />
                    </Reference>
                    <UTCDate>
                        <xsl:value-of select="java:frenchDateToUTC(FrenchDate)" />
                    </UTCDate>
                </TransformedOffer>
            </xsl:for-each>
        </Result>
    </xsl:template>
</xsl:stylesheet>
