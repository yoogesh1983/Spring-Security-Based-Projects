<!DOCTYPE html>
<html lang="en">
<head>
    <title>Html Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Lato:100,300,400,700,900" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/demo.css" rel="stylesheet" type="text/css">
</head>

<body>
    <!-- Header Section -->
    <header class="header">
       <div class="header__logo-box">
            <img src="${pageContext.request.contextPath}/static/images/demo/logo-white.png" alt="logo" class="header__logo"/>
       </div>
		
       <div class="header__middle-section-box">
            <div class="mediumFont">Center Section</div>
       </div>
    </header>

    <main>
      <!-- Body Section -->
      <section class="section-about">
        <div class="Container">
          <div class="columnOne">
            <div class="mediumFont">First Div</div>
          </div>
          <div class="columnTwo">
            <div class="mediumFont">Second Div</div>
          </div>
        </div>
      </section>
    
		<section class="section-feature">
      <div class="Container">
        <div class="columnOne">
          <div class="mediumFont">First Div</div>
        </div>
        <div class="columnTwo">
          <div class="mediumFont">Second Div</div>
        </div>
      </div>
    </section>
    
    <!-- Footer Section -->
    <footer>
      <span>This is a Footer</span>
    </footer>
  </main>
</body>
</html>