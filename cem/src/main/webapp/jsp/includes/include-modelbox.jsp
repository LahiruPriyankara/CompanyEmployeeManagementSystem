<%-- 
    Document   : include-modelbox
    Created on : Dec 21, 2020, 3:38:39 PM
    Author     : sits_lahirupr
--%>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog" style="box-shadow: 0 0 10px 8px #000000;">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header" style="background-color: #000000;">
                <div style="float: left;"><span class="modal-title" style="color: #FFFFFF"><b>SELECTED USER DETAILS</b></span> </div>
                <button type="button" class="close" data-dismiss="modal" style="color: #FFFFFF;"><span >&times;</span></button>                           
            </div>
            <div class="modal-body" id="modelDivData">
                <p>Loading...Please wait..</p>
            </div>
            <div class="modal-footer">
                <button id="saveBtn" type="button" class="btn btn-success" onclick="saveBankUser()" style="display: none">Save</button> 
                <button type="button" id="modelCloseBtn" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

