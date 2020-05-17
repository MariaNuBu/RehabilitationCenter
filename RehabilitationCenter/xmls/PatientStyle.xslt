<!DOCTYPE html>


<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />
<xsl:template match="/">
<HTML lang="es" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <TITLE>REHABILITATION CENTER</TITLE>
    <META charset="UTF-8" />
    <link href="https://fonts.googleapis.com/css?family=Anton" rel="stylesheet"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <LINK rel="icon" hreF="Favicon.png" />
</HEAD>

<BODY background="images/rehabilitationcenter.jpg">
	
    <div class="contenedor">
        <div class="menubar" style="border-bottom:1px solid black">
		
            <span style="color:#7C7D7D">MENU</span>
				&#160;
            <a ><span style="color: #E9ECEF">Contact us</span></a>
				&#160;			
            <a ><span style="color: #E9ECEF">Our doctors</span></a>
				&#160;
            <a ><span style="color: #E9ECEF">Our physical therapists</span></a>
				&#160;
            <a ><span style="color: #E9ECEF">Location</span></a>

        </div>
		
		<div class="row" style="padding:1%; padding-top:5%">
			<div class="col-sm">
				<img src="images/daniel.jpg" width="100%" height="97%"></img>
			</div>	
			
			<div class="col-sm">
				<table class="table table-bordered table-dark">
				  <thead class="thead-light">
					<tr>
					  <th scope="col" colspan="4" style="text-align:center">Patient</th>
					</tr>
				  </thead>
				  <tbody>
					<tr>
					  <th scope="row">Name</th>
					  <td> <xsl:value-of select = "/patient/@name" /></td>
					  <th scope="row">Date of birth</th>
					  <td> <xsl:value-of select = "/patient/dob" /></td>
					</tr>					
					<tr>
					  <th scope="row">Address</th>
					  <td> <xsl:value-of select = "/patient/address" /></td>
					  <th scope="row">Phone Number</th>
					  <td> <xsl:value-of select = "/patient/phoneNumber" /></td>
					</tr>
					<tr>
					  <th scope="row">Email</th>
					  <td> <xsl:value-of select = "/patient/eMail" /></td>
					  <th scope="row">Sport</th>
					  <td> <xsl:value-of select = "/patient/sport" /></td>
					</tr>
					<tr>
					  <th scope="row">Disability</th>
					  <td> <xsl:value-of select = "/patient/disability"/></td>
					</tr>
				  </tbody>
				  <thead class="thead-light">
					<tr>
					  <th scope="col" colspan="4" style="text-align:center">Medical History</th>
					</tr>
				  </thead>
				  <tbody>
					<tr>
					  <th scope="row">ID</th>
					  <td> <xsl:value-of select = "/patient/medicalHistory/@id"/></td>
					  <th scope="row">Diseases</th>
					  <td><xsl:value-of select = "/patient/medicalHistory/Diseases"/></td>
					</tr>					
					<tr>
					  <th scope="row">Allergies</th>
					  <td> <xsl:value-of select = "/patient/medicalHistory/Allergies"/></td>
					  <th scope="row">Surgeries</th>
					  <td> Several surgeries in the left leg</td>
					</tr>
					<tr>
					  <th scope="row">Weight (kg)</th>
					  <td> <xsl:value-of select = "/patient/medicalHistory/WeightKg"/></td>
					  <th scope="row">Height(cm)</th>
					  <td> <xsl:value-of select = "/patient/medicalHistory/HeightCm"/></td>
					</tr>
				  </tbody>
				</table>
			</div>			
		</div>
		
		<div class="row" style="padding:2%">
			<table class="table table-bordered">
				  <thead class="thead-dark">
					<tr>
					  <th scope="col" colspan="8" style="text-align:center">Appointments</th>
					</tr>
				  </thead>
				  <tbody style="background-color:#E9ECEF">
					<tr>
					  <th scope="row">Date</th>
					  <th>Time</th>
					  <th>Doctor</th>
					  <th>Email</th>
					  <th>Specialty</th>
					  <th>PhysicalTherapist</th>
					  <th>Email</th>
					  <th>Sport</th>
					</tr>
					<xsl:for-each select="//appointment">
						<tr>
						<td scope="row"><xsl:value-of select="date" /></td>
						  <td scope="row">12:30:00</td>
						  <td scope="row"><xsl:value-of select="doc/@name" /></td>
						  <td scope="row"><xsl:value-of select="doc/eMail" /></td>
						  <td scope="row"><xsl:value-of select="doc/specialty" /></td>
						  <td scope="row"><xsl:value-of select="pt/@name" /></td>
						  <td scope="row"><xsl:value-of select="pt/eMail" /></td>
						  <td scope="row"><xsl:value-of select="pt/typeSport" /></td>
						</tr>
					</xsl:for-each>					
				  </tbody>
			</table>
		</div>
     </div>
</BODY>
</HTML>
</xsl:template>
</xsl:stylesheet>



