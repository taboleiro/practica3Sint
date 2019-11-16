<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" />
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr bgcolor="#9acd32">
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
                    <xsl:for-each select="Programacion/Canal">
                        <xsl:variable name="nombreCanal" select="NombreCanal"/>
                        <xsl:variable name="id" select="@idCanal"/>
                        <xsl:variable name="grupo" select="Grupo"/>
                        <xsl:variable name="langCanal" select="@lang"/>
                        <xsl:for-each select="Programa">
                            <tr>
                                <td><xsl:value-of select="NombrePrograma"/></td>
                                <td><xsl:value-of select="@edadminima"/></td>
                                <td><xsl:value-of select="@langs"/></td>
                                <td><xsl:value-of select="HoraInicio"/></td>
                                <td><xsl:value-of select="Duracion"/></td>
                                <td><xsl:value-of select="HoraFin"/></td>
                                <td><xsl:value-of select="OtraEmision"/></td>
                                <td><xsl:value-of select="text()"/></td>
                                <td><xsl:value-of select="Categoria"/></td>
                                <td><xsl:value-of select="$langCanal"/></td>
                                <td><xsl:value-of select="$id"/></td>
                                <td><xsl:value-of select="$nombreCanal"/></td>
                                <td><xsl:value-of select="$grupo"/></td>
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>