<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title"/></title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; line-height: 1.6; }
        .container { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
        header { background: #333; color: #fff; padding: 1rem 0; }
        header h1 { display: inline-block; margin-right: 2rem; }
        nav { background: #444; padding: 0.5rem 0; }
        nav ul { list-style: none; }
        nav ul li { display: inline; margin-right: 20px; }
        nav ul li a { color: #fff; text-decoration: none; }
        nav ul li a:hover { color: #ddd; }
        main { min-height: 500px; padding: 2rem 0; }
        footer { background: #333; color: #fff; text-align: center; padding: 1rem 0; margin-top: 2rem; }
        .message { padding: 10px; margin: 10px 0; border-radius: 4px; }
        .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        table th, table td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        table th { background: #f4f4f4; font-weight: bold; }
        table tr:hover { background: #f5f5f5; }
        .btn { display: inline-block; padding: 8px 16px; margin: 4px 2px; text-decoration: none; border-radius: 4px; cursor: pointer; border: none; }
        .btn-primary { background: #007bff; color: #fff; }
        .btn-success { background: #28a745; color: #fff; }
        .btn-danger { background: #dc3545; color: #fff; }
        .btn-secondary { background: #6c757d; color: #fff; }
        .btn:hover { opacity: 0.8; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .actions { margin-top: 20px; }
    </style>

    <s:url var="mainJsUrl" value="/ui/main.js" />
    <script type="module" src="${mainJsUrl}" nonce="${nonce}"></script>
</head>
<body>
    <header>
        <div class="container">
            <tiles:insertAttribute name="header"/>
        </div>
    </header>

    <nav>
        <div class="container">
            <tiles:insertAttribute name="menu"/>
        </div>
    </nav>

    <main>
        <div class="container">
            <s:if test="hasActionMessages()">
                <div class="message success">
                    <s:actionmessage/>
                </div>
            </s:if>

            <s:if test="hasActionErrors()">
                <div class="message error">
                    <s:actionerror/>
                </div>
            </s:if>

            <tiles:insertAttribute name="body"/>
        </div>
    </main>

    <footer>
        <div class="container">
            <tiles:insertAttribute name="footer"/>
        </div>
    </footer>
</body>
</html>
