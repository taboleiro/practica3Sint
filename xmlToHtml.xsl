<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsd="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <table>
                    <tr>
                        <th>Programa</th>
                        <th>Edad mínima</th>
                        <th>langs</th>
                        <th>Hora Inicio</th>
                        <th>Duración</th>
                        <th>Hora fin</th>
                        <th>Otra emisión</th>
                        <th>Texto</th>
                        <th>Categoría</th>
                        <th>lang canal</th>
                        <th>Id canal</th>
                        <th>Nombre canal</th>
                        <th>Grupo canal</th>
                    </tr>
                    <xsl:for-each select="programacion/Canal">
                        <xsl:variable name="nombreCanal" select="NombreCanal"/>
                        <xsl:variable name="idCanal" select="idCanal"/>
                        <xsl:variable name="grupo" select="Grupo"/>
                        <xsl:variable name="lang" select="lang"/>
                        <xsl:for-each select="Programa">
                            <tr>
                                <td><xsd:value-of select="NombrePrograma"</td>
                                <td><xsd:value-of select="edadminima"</td>
                                <td><xsd:value-of select="lags"</td>
                                <td><xsd:value-of select="HoraInicio"</td>
                                <td><xsd:value-of select="Duracion"</td>
                                <td><xsd:value-of select="HoraFin"</td>
                                <td><xsd:value-of select="OtraEmision"</td>
                                <td><xsd:value-of select="Texto"</td>
                                <td><xsd:value-of select="Categoria"</td>
                                <td><xsd:copy-of select="$nombreCanal"</td>
                                <td><xsd:copy-of select="$idCanal"</td>
                                <td><xsd:copy-of select="grupo"</td>
                                <td><xsd:copy-of select="lang"</td>
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>