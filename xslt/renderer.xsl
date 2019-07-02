<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/solutions/solution">
        <html>
            <head>
                <STYLE type="text/css">
                    html, body {
                        font-family: sans-serif;
                        font-size: 1em;
                    }
                    h2 {
                        font-size: 150%;
                    }
                    table.wikitable {
                        background-color: #F9F9F9;
                        border: 1px solid #AAAAAA;
                        border-collapse: collapse;
                        color: black;
                        margin: 1em 1em 1em 0;
                    }
                    table.wikitable > tr > th, table.wikitable > tr > td, table.wikitable > * > tr > th, table.wikitable > * > tr > td {
                        border: 1px solid #AAAAAA;
                        padding: 0.2em;
                    }
                    table.wikitable > tr > th, table.wikitable > * > tr > th {
                        background-color: #F2F2F2;
                        text-align: center;
                    }
                    table.wikitable > caption {
                        font-weight: bold;
                    }
                </STYLE>
            </head>
            <body>
                <h2>Solution</h2>
                <table class="wikitable">
                    <tboby>
                    <tr>
                        <th>House</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@position"/>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <th>Color</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@color"/>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <th>Nationality</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@nationality"/>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <th>Drink</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@drink"/>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <th>Smoke</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@smoke"/>
                        </xsl:for-each>
                    </tr>

                    <tr>
                        <th>Pet</th>
                        <xsl:for-each select="house">
                            <xsl:apply-templates select="@pet"/>
                        </xsl:for-each>
                    </tr>
                    </tboby>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="@position">
        <th>
            <xsl:value-of select="."/>
        </th>
    </xsl:template>

    <xsl:template match="@color|@nationality|@drink|@smoke|@pet">
        <td>
            <xsl:value-of select="."/>
        </td>
    </xsl:template>

</xsl:stylesheet>