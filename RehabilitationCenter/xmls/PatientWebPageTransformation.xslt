
<html>
<head></head>
<body>
<div class="topnav">
  <a class="active" href="#home">Home</a>
  <a href="#news">News</a>
  <a href="#contact">Contact</a>
  <a href="#about">About</a>
</div>
<p>
<b>Name: <xsl:value-of select = "/patient/@name" /></b>
Date of Birth: <xsl:value-of select = "/patient/dob" />
Address: <xsl:value-of select = "/patient/address" />
Phone Number: <xsl:value-of select = "/patient/phoneNumber" />
Email: <xsl:value-of select = "/patient/eMail" />
Sport: <xsl:value-of select = "/patient/sport" />
Disability: <xsl:value-of select = "/patient/disability"/>
Medical History:  <xsl:value-of select = "/patient/medicalHistory/@ID"/>
Diseases:  <xsl:value-of select = "/patient/medicalHistory/Diseases"/>
Allergies: <xsl:value-of select = "/patient/medicalHistory/Allergies"/>
Weight (kg): <xsl:value-of select = "/patient/medicalHistory/WeightKg"/>
Height (cm): <xsl:value-of select = "/patient/medicalHistory/HeightCm"/>
Appointments:
		<table>
			<th>Appointment</th>
			<xsl:for-each select="/patient/medicalHistory/appointments">
			<tr>
				<td>Date: <xsl:value-of select="date" /></td>
				<td>Doctor: <xsl:value-of select="doc/name" />, <xsl:value-of select="doc/eMail" /><xsl:value-of select="doc/speciality" /></td>
				<td>Physical Therapist: <xsl:value-of select="pt/@name" />, <xsl:value-of select="pt/eMail" /><xsl:value-of select="pt/typeSport" /></td>
				
			</tr>
			</xsl:for-each>
		</table>
</p>
</body>
</html>