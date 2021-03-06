package com.barclays.indiacp.controller;

import com.barclays.indiacp.model.APIData;
import com.barclays.indiacp.model.APIResponse;
import com.barclays.indiacp.service.GethHttpService;
import com.barclays.indiacp.config.JsonMethodArgumentResolver.JsonBodyParam;
import com.barclays.indiacp.error.APIException;
import com.barclays.indiacp.model.APIError;
import com.barclays.indiacp.model.Node;
import com.barclays.indiacp.model.Peer;
import com.barclays.indiacp.service.NodeService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Samer Falah
 */
@RestController
@RequestMapping(value = "/api/node",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class NodeController extends BaseController {

    @Autowired
    private GethHttpService gethService;

    @Autowired
    private NodeService nodeService;

    @RequestMapping({ "/get" })
    protected ResponseEntity<APIResponse> doGet() throws APIException {

        Node node = nodeService.get();

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(new APIData(node.getId(), "node", node));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping("/update")
	public ResponseEntity<APIResponse> update(
	        @JsonBodyParam(required = false) String logLevel,
			@JsonBodyParam(required = false) String networkId,
			@JsonBodyParam(required = false) String identity,
			@JsonBodyParam(required = false) Object committingTransactions,
			@JsonBodyParam(required = false) String extraParams,
			@JsonBodyParam(required = false) String genesisBlock) throws APIException {

        Boolean isMining = null;

        try {

            Integer logLevelInt = null,
                    networkIDInt = null;

            if (!StringUtils.isEmpty(logLevel)) {
                logLevelInt = Integer.parseInt(logLevel);
            }

            if (!StringUtils.isEmpty(networkId)) {
                networkIDInt = Integer.parseInt(networkId);
            }

            if (committingTransactions != null) {
                if (committingTransactions instanceof String && StringUtils.isNotBlank((String) committingTransactions)) {
                    isMining = Boolean.parseBoolean((String) committingTransactions);
                } else {
                    isMining = (Boolean) committingTransactions;
                }
            }

            nodeService.update(logLevelInt, networkIDInt, identity, isMining,
                    extraParams, genesisBlock);

            return doGet();

        } catch (NumberFormatException ne) {
            APIError err = new APIError();
            err.setStatus("400");
            err.setTitle("Input Formatting Error");

            APIResponse res = new APIResponse();
            res.addError(err);

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/peers/add")
    public ResponseEntity<APIResponse> addPeer(@JsonBodyParam String address) throws APIException {
        if (StringUtils.isBlank(address)) {
            return new ResponseEntity<>(
                    new APIResponse().error(new APIError().title("Missing param 'address'")),
                    HttpStatus.BAD_REQUEST);
        }
        boolean added = nodeService.addPeer(address);
        return new ResponseEntity<>(APIResponse.newSimpleResponse(added), HttpStatus.OK);
    }

    @RequestMapping("/peers")
    public ResponseEntity<APIResponse> peers() throws APIException {
        List<Peer> peers = nodeService.peers();
        List<APIData> data = new ArrayList<>();
        if (peers != null && !peers.isEmpty()) {
            for (Peer peer : peers) {
                data.add(new APIData(peer.getId(), "peer", peer));
            }
        }

        APIResponse res = new APIResponse().data(data);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("/start")
    protected @ResponseBody ResponseEntity<APIResponse> startGeth() {
        Boolean started = gethService.start();
        return new ResponseEntity<>(APIResponse.newSimpleResponse(started), HttpStatus.OK);
    }

    @RequestMapping("/stop")
    protected @ResponseBody ResponseEntity<APIResponse> stopGeth() {
        Boolean stopped = gethService.stop();
        gethService.deletePid();
        return new ResponseEntity<>(APIResponse.newSimpleResponse(stopped), HttpStatus.OK);
    }

    @RequestMapping("/restart")
    protected @ResponseBody ResponseEntity<APIResponse> restartGeth() {
        Boolean stopped = gethService.stop();
        Boolean deleted = gethService.deletePid();
        Boolean restarted = false;
        if (stopped && deleted) {
            restarted = gethService.start();
        }
        return new ResponseEntity<>(APIResponse.newSimpleResponse(restarted), HttpStatus.OK);
    }

    @RequestMapping("/reset")
    protected @ResponseBody ResponseEntity<APIResponse> resetGeth() {
        Boolean reset = gethService.reset();
        return new ResponseEntity<>(APIResponse.newSimpleResponse(reset), HttpStatus.OK);
    }

    @RequestMapping("/settings/reset")
    protected @ResponseBody ResponseEntity<APIResponse> resetNodeInfo() {
        Boolean reset = nodeService.reset();
        return new ResponseEntity<>(APIResponse.newSimpleResponse(reset), HttpStatus.OK);
    }

}
